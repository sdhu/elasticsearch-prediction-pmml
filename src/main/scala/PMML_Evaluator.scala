package com.sdhu.elasticsearchprediction.pmml

import com.mahisoft.elasticsearchprediction.plugin.domain.{IndexValue, IndexAttributeDefinition}

import org.dmg.pmml.{ IOUtil, FieldName }
import org.jpmml.evaluator.{ ModelEvaluatorFactory, EvaluatorUtil, Evaluator }
import org.jpmml.manager.PMMLManager
import java.io.InputStream

// jpmml needs a Java map, which is converted from a *mutable* map
import scala.collection.mutable.{ Map ⇒ MMap }
import scala.collection.JavaConverters._
import scala.util.Try

import java.util.{ Collection, List ⇒ JList }
import java.io.{ File, FileInputStream}

case class PMML_Model(
  evaluator: Evaluator, 
  colFieldNames: Seq[FieldName],
  targetField: FieldName
)

object PMML_Loader {
  /* 
   * Load the model from absolute path
   */
  def loadEvaluator(
    modelPath: String, 
    mappings: JList[IndexAttributeDefinition],
    targetName: String
  ): Option[PMML_Model] = {
    Try { 
      val colFieldNames = mappings.asScala.map(f ⇒ new FieldName(f.getName()))

      val is = new FileInputStream(new File(modelPath))
      val pmml = IOUtil.unmarshal(is)
      val pmmlManager = new PMMLManager(pmml)
      
      PMML_Model(
        pmmlManager
          .getModelManager(null, ModelEvaluatorFactory.getInstance)
          .asInstanceOf[Evaluator],
        colFieldNames,
        new FieldName(targetName)
      )
    }.toOption
  }
}


/** Trait for a base evaluator data structure 
 *  
 *  TODO: Currently only supporting Double type
 *
 */
case class PMML_BaseEval(
  model: PMML_Model,
  values: Collection[IndexValue]
) {

   // PMML evaluator objects require a [[ java.util.Map[FieldName, Any] ]]
  lazy val formattedDataPoints: java.util.Map[FieldName, _] = {
    val fieldsAndValues = model.colFieldNames
      .zip(ReadUtil.cIndVal2Array(values, Map[String, Double]()))
      .map{ x ⇒ x._1 -> x._2 }
    MMap(fieldsAndValues:_*).asJava
  }

  def predict(): Double = {
    val predictionMap = model.evaluator
      .evaluate(formattedDataPoints)
      .get(model.targetField)

    EvaluatorUtil.decode(predictionMap)
      .asInstanceOf[Double]
  }
}

package com.sdhu.elasticsearchprediction.pmml

import com.mahisoft.elasticsearchprediction.plugin.engine.PredictorEngine
import com.mahisoft.elasticsearchprediction.plugin.domain.{IndexValue, IndexAttributeDefinition}
import com.mahisoft.elasticsearchprediction.plugin.exception.PredictionException

import java.util.{ Collection, List ⇒ JList }

class PMML_PredictorEngineBuilder (
	val modelPath: String, 
	val mappings: JList[IndexAttributeDefinition],
	val targetName: String
) extends PredictorEngine {
  
  private var _model: Option[PMML_Model] = None

  override def getPrediction(values: Collection[IndexValue]): Double = {
    _model match {
			case Some(m) ⇒ PMML_BaseEval(m, values).predict 
			case None ⇒ throw new PredictionException("Empty model")
    }
  }
  
  def readModel(): Option[PMML_Model] = {
    _model = PMML_Loader.loadEvaluator(modelPath, mappings, targetName)
    _model
  }

  def getOptModel: Option[PMML_Model] = _model
}


class PMML_PredictorEngine (
	modelPath: String, 
	mappings: JList[IndexAttributeDefinition],
	targetName: String
) {
	def getPM: PredictorEngine = {
		val pm = new PMML_PredictorEngineBuilder(modelPath, mappings, targetName)
		pm.readModel()
		pm
	}
}




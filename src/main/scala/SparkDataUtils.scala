package com.sdhu.elasticsearchprediction.pmml

import com.mahisoft.elasticsearchprediction._
import plugin.domain.{ IndexValue, IndexAttributeDefinition }
import domain.DataType

import java.util.Collection

import scala.util.control.Exception._
import scala.collection.JavaConversions._
import scala.util.Random

object CsvUtil extends Serializable {

  implicit class RichArrayString(val a: Array[String]) extends Serializable {
    def toDoubleOpt(i: Int): Option[Double] =  
      catching(classOf[NumberFormatException]).opt(a(i).toDouble)
    
    def toDoubleEither(i: Int): Either[Double, String] = {
      this.toDoubleOpt(i) match {
        case Some(d) ⇒ Left(d)
        case None ⇒ Right(a(i))
      }
    }

    def toDoubleArray(cm: Map[String, Double]): Array[Double] = {
      a.zipWithIndex.map{ case (v,i) ⇒ {
        this.toDoubleOpt(i) match {
          case Some(d) ⇒ d
          case None ⇒ cm.getOrElse(v, 0.0)
        }
      }}
    }
  }
}


object ReadUtil extends Serializable {
  import CsvUtil._
  
  def cIndVal2Array(v: Collection[IndexValue], cm: Map[String, Double]): Array[Double] = {
    v.map(x ⇒ x.getDefinition.getType match {
        case DataType.DOUBLE ⇒ x.getValue.asInstanceOf[Double].toString
        case _ ⇒ x.getValue.asInstanceOf[String]
      }).toArray.toDoubleArray(cm)
  }

  // not using IdexAttributeDefinition ... just set it to double
  def arr2CIndVal(v: Array[String]): Collection[IndexValue] = {
    val ret = v.map(s ⇒ new IndexValue(
          new IndexAttributeDefinition("notUsed", DataType.STRING),
          s
        ))
    
    asJavaCollection[IndexValue](ret)
  }
}


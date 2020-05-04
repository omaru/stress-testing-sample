package org.example

import java.util.UUID

import org.example.model.VoucherCode

object HelloWorld {

  def main(args: Array[String]) = {
    println("Hello world")
    val promotionID = UUID.randomUUID().toString
    val voucherCodesFeeder =(promotionId:String, numberOfCodes:Int)=> Iterator.continually({
      val test = List.fill(numberOfCodes)(new VoucherCode(promotionId,UUID.randomUUID().toString))
      s"""{"voucherCodes":[${test.mkString(",")}]}"""
    })
    println(voucherCodesFeeder(promotionID,100).next())
    println(voucherCodesFeeder(promotionID,999).next())
    println(voucherCodesFeeder(promotionID,100).next())
  }
  def voucherCodes(promotionId:String, numberOfCodes:Int):Iterator[String]={
    Iterator.continually({
      val test = List.fill(numberOfCodes)(new VoucherCode(promotionId,UUID.randomUUID().toString))
      s"""{"voucherCodes":[${test.mkString(",")}]}"""
    })
  }
}

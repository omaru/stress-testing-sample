package org.example.model

class VoucherCode (val promotionId:String, val code:String){

  override def toString: String =
    s"""{"promotionId":"$promotionId", "code":"$code"}"""

}

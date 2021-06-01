package kr.ac.konkuk.cse.examm12

data class Product(var pId: Int, var pName: String, var pQuantity: Int) {
    //firebase로부터 데이터를 fetch할 때는 default constructor를 사용한다
    constructor() : this(0, "noinfo", 0)
}

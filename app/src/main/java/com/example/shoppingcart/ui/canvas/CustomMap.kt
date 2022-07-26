package com.example.shoppingcart.ui.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View

class CustomMap(
    context: Context,
    attr: AttributeSet
) : View(context,attr) {

    private var currentSection: String = ""
    private var destination: String = ""
    private var mDrawPaint: Paint? = null
//    private var mDrawPaint2: Paint? = null
    private var color = Color.RED
//    private var color2 = Color.CYAN

    private val p1 : Point = Point(625,163)
    private val p2 : Point = Point(888,163)
    private val p3 : Point = Point(1095,163)

    private val p4 : Point = Point(625,430)
    private val p5 : Point = Point(888,430)
    private val p6 : Point = Point(1095,430)

    private val p7 : Point = Point(625,690)
    private val p8 : Point = Point(888,690)
    private val p9 : Point = Point(1095,690)

    init {
        mDrawPaint = Paint()
        mDrawPaint?.color
        mDrawPaint!!.color = color
        mDrawPaint!!.strokeWidth = 18f
        mDrawPaint!!.style = Paint.Style.FILL
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND

//        mDrawPaint2 = Paint()
//        mDrawPaint2?.color
//        mDrawPaint2!!.color = color2
//        mDrawPaint2!!.strokeWidth = 18f
//        mDrawPaint2!!.style = Paint.Style.FILL
//        mDrawPaint2!!.strokeJoin = Paint.Join.ROUND
//        mDrawPaint2!!.strokeCap = Paint.Cap.ROUND
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawPath(currentSection,destination,canvas)
    }

    private fun drawPath(section1: String, section2: String, canvas: Canvas?){
        if (section1 == section2){
            return
        }else if(section1 == "" || section2 == ""){
            return
        }else{
            mDrawPaint?.let {
                when(section1){
                    "SeaFood,Meat,Bulk" ->{
                        when(section2){
                            "Dairy,eggs,Cheese"->{
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p3.x.toFloat(),p3.y.toFloat(),it)
                            }
                            "Bakery,PreparedFood"->{
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p3.x.toFloat(),p3.y.toFloat(),it)
                                canvas?.drawLine(p3.x.toFloat(),p3.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                            }
                            "Frozen"->{
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p2.x.toFloat(),p2.y.toFloat(),it)
                                canvas?.drawLine(p2.x.toFloat(),p2.y.toFloat(),p5.x.toFloat(),p5.y.toFloat(),it)
                            }
                            "Grocery"->{
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p2.x.toFloat(),p2.y.toFloat(),it)
                            }
                            "Florist"->{
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p7.x.toFloat(),p7.y.toFloat(),it)
                            }
                            else -> {}
                        }
                    }
                    "Dairy,eggs,Cheese" ->{
                        when(section2){
                            "SeaFood,Meat,Bulk"->{
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p3.x.toFloat(),p3.y.toFloat(),it)
                            }
                            "Bakery,PreparedFood"->{
                                canvas?.drawLine(p3.x.toFloat(),p3.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                            }
                            "Frozen"->{
                                canvas?.drawLine(p3.x.toFloat(),p3.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                            }
                            "Grocery"->{
                                canvas?.drawLine(p3.x.toFloat(),p3.y.toFloat(),p2.x.toFloat(),p2.y.toFloat(),it)
                            }
                            "Florist"->{
                                canvas?.drawLine(p3.x.toFloat(),p3.y.toFloat(),p9.x.toFloat(),p9.y.toFloat(),it)
                                canvas?.drawLine(p7.x.toFloat(),p7.y.toFloat(),p9.x.toFloat(),p9.y.toFloat(),it)
                            }
                            else -> {}
                        }
                    }
                    "Bakery,PreparedFood" ->{
                        when(section2){
                            "SeaFood,Meat,Bulk"->{
                                canvas?.drawLine(p6.x.toFloat(),p6.y.toFloat(),p3.x.toFloat(),p3.y.toFloat(),it)
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p3.x.toFloat(),p3.y.toFloat(),it)
                            }
                            "Dairy,eggs,Cheese"->{
                                canvas?.drawLine(p3.x.toFloat(),p3.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                            }
                            "Frozen"->{
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                            }
                            "Grocery"->{
                                canvas?.drawLine(p6.x.toFloat(),p6.y.toFloat(),p3.x.toFloat(),p3.y.toFloat(),it)
                                canvas?.drawLine(p2.x.toFloat(),p2.y.toFloat(),p3.x.toFloat(),p3.y.toFloat(),it)
                            }
                            "Florist"->{
                                canvas?.drawLine(p6.x.toFloat(),p6.y.toFloat(),p9.x.toFloat(),p9.y.toFloat(),it)
                                canvas?.drawLine(p7.x.toFloat(),p7.y.toFloat(),p9.x.toFloat(),p9.y.toFloat(),it)
                            }
                            else -> {}
                        }
                    }
                    "Frozen" ->{
                        when(section2){
                            "SeaFood,Meat,Bulk"->{
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p2.x.toFloat(),p2.y.toFloat(),it)
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p2.x.toFloat(),p2.y.toFloat(),it)
                            }
                            "Dairy,eggs,Cheese"->{
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                                canvas?.drawLine(p3.x.toFloat(),p3.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                            }
                            "Bakery,PreparedFood"->{
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                            }
                            "Grocery"->{
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p2.x.toFloat(),p2.y.toFloat(),it)
                            }
                            "Florist"->{
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p8.x.toFloat(),p8.y.toFloat(),it)
                                canvas?.drawLine(p8.x.toFloat(),p8.y.toFloat(),p7.x.toFloat(),p7.y.toFloat(),it)
                            }
                            else -> {}
                        }
                    }
                    "Grocery" ->{
                        when(section2){
                            "SeaFood,Meat,Bulk"->{
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p2.x.toFloat(),p2.y.toFloat(),it)
                            }
                            "Dairy,eggs,Cheese"->{
                                canvas?.drawLine(p2.x.toFloat(),p2.y.toFloat(),p3.x.toFloat(),p3.y.toFloat(),it)
                            }
                            "Bakery,PreparedFood"->{
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p6.x.toFloat(),p6.y.toFloat(),it)
                            }
                            "Frozen"->{
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p2.x.toFloat(),p2.y.toFloat(),it)
                            }
                            "Florist"->{
                                canvas?.drawLine(p5.x.toFloat(),p5.y.toFloat(),p4.x.toFloat(),p4.y.toFloat(),it)
                                canvas?.drawLine(p4.x.toFloat(),p4.y.toFloat(),p7.x.toFloat(),p7.y.toFloat(),it)
                            }
                            else -> {}
                        }
                    }
                    "Florist" ->{
                        when(section2){
                            "SeaFood,Meat,Bulk"->{
                                canvas?.drawLine(p1.x.toFloat(),p1.y.toFloat(),p7.x.toFloat(),p7.y.toFloat(),it)
                            }
                            "Dairy,eggs,Cheese"->{
                                canvas?.drawLine(p7.x.toFloat(),p7.y.toFloat(),p9.x.toFloat(),p9.y.toFloat(),it)
                                canvas?.drawLine(p3.x.toFloat(),p3.y.toFloat(),p9.x.toFloat(),p9.y.toFloat(),it)
                            }
                            "Bakery,PreparedFood"->{
                                canvas?.drawLine(p7.x.toFloat(),p7.y.toFloat(),p9.x.toFloat(),p9.y.toFloat(),it)
                                canvas?.drawLine(p6.x.toFloat(),p6.y.toFloat(),p9.x.toFloat(),p9.y.toFloat(),it)
                            }
                            "Frozen"->{
                                canvas?.drawLine(p7.x.toFloat(),p7.y.toFloat(),p8.x.toFloat(),p8.y.toFloat(),it)
                            }
                            "Grocery"->{
                                canvas?.drawLine(p7.x.toFloat(),p7.y.toFloat(),p8.x.toFloat(),p8.y.toFloat(),it)
                                canvas?.drawLine(p8.x.toFloat(),p8.y.toFloat(),p2.x.toFloat(),p2.y.toFloat(),it)
                            }
                            else -> {}
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun setValues(section1: String, section2: String){
        this.currentSection = section1
        this.destination = section2
        invalidate()
    }
}

//        mDrawPaint?.let {
//            canvas?.drawPoint(p1.x.toFloat(),p1.y.toFloat(),mDrawPaint!!)
//            canvas?.drawPoint(p2.x.toFloat(),p2.y.toFloat(),mDrawPaint!!)
//            canvas?.drawPoint(p3.x.toFloat(),p3.y.toFloat(),mDrawPaint!!)
//            canvas?.drawPoint(p4.x.toFloat(),p4.y.toFloat(),mDrawPaint!!)
//            canvas?.drawPoint(p5.x.toFloat(),p5.y.toFloat(),mDrawPaint!!)
//            canvas?.drawPoint(p6.x.toFloat(),p6.y.toFloat(),mDrawPaint!!)
//            canvas?.drawPoint(p7.x.toFloat(),p7.y.toFloat(),mDrawPaint!!)
//            canvas?.drawPoint(p8.x.toFloat(),p8.y.toFloat(),mDrawPaint!!)
//            canvas?.drawPoint(p9.x.toFloat(),p9.y.toFloat(),mDrawPaint!!)
//        }
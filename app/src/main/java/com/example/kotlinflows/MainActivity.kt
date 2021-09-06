package com.example.kotlinflows

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mFlow: Flow<Int>
    //private  var mFlow: Flow<Int> = flowOf(0,1,2,3,4,5,6,7,8,9,10) Flow Builder 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFlow()
        initClickListener()
    }

    private fun setupFlow() {
        //Flow Builder 2
        mFlow = flow {
            runOnUiThread {
                Toast.makeText(this@MainActivity, "Start Flow", Toast.LENGTH_SHORT).show()
            }
            (0..10).forEach {
                delay(1000)
                emit(it)
            }
        }.flowOn(Dispatchers.IO)

        //(0..10).asFlow().onEach { delay(1000) }.flowOn(Dispatchers.IO) Flow Builder 3

        /*channelFlow {
             (0..10).forEach {
                 send(it)
             }
         }.flowOn(Dispatchers.IO) Flow Builder 4 */
    }

    private fun initClickListener() {
        btn_create_flow.setOnClickListener {
            tv_emitted_flow_value.text = "Data emiited by Flow :- "
            CoroutineScope(Dispatchers.Main).launch {
                mFlow.collect {
                    val msg = tv_emitted_flow_value.text.toString() + "$it "
                    tv_emitted_flow_value.text = msg
                }
            }
        }
    }
}

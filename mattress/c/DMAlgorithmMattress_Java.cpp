//
//  DMAlgorithmMattress_J.cpp
//  smattress
//
//  Created by Frank陈 on 15/10/20.
//  Copyright © 2015年 Frank陈. All rights reserved.
//

#include <jni.h>
#include <iostream>
#include <fstream>

#include "co_darma_smartmattress_analysis_healthAlgorithms_basic_DMAlgorithmMattress.h"
//#include "co_darma_DMAlgorithmMattress.h"

using namespace std;
JNIEXPORT jintArray JNICALL Java_co_darma_smartmattress_analysis_healthAlgorithms_basic_DMAlgorithmMattress_offlineStatusCalulation 
(JNIEnv * env, jobject object, jintArray intarray)
{

    jsize length = env->GetArrayLength(intarray);
    jint* opticData = env->GetIntArrayElements(intarray, 0);

    vector<int> newVector;
    for(int i = 0 ;i < length;++i){
         newVector.push_back(opticData[i]);
    }

    env->ReleaseIntArrayElements(intarray,opticData,0);
    // call the alogrithm....
    vector<int> result = OfflineStatusCalculation(newVector, 5*SAMPLE_RATE,1000,600,5000,5,62000);

    int result_size_i = result.size();
    jintArray finalResult = env->NewIntArray(result_size_i);

    int* pint= new int[result_size_i];
    for(int i = 0 ;i < result_size_i;i++){
       pint[i] = result[i];
    }
    env->SetIntArrayRegion(finalResult,0,result_size_i, pint);
    delete pint;
    return finalResult;
}
JNIEXPORT jintArray JNICALL Java_co_darma_smartmattress_analysis_healthAlgorithms_basic_DMAlgorithmMattress_offlineStatusCalForMeddo 
(JNIEnv * env, jobject object, jintArray intarray)
{

    jsize length = env->GetArrayLength(intarray);
    jint* opticData = env->GetIntArrayElements(intarray, 0);

    vector<int> newVector;
    for(int i = 0 ;i < length;++i){
         newVector.push_back(opticData[i]);
    }

    env->ReleaseIntArrayElements(intarray,opticData,0);
    // call the alogrithm....

    vector<int> result = OfflineStatusCalculation(newVector,SAMPLE_RATE,3000.0,800,5000,5,62000);
    //end....

    int result_size_i = result.size();
    jintArray finalResult = env->NewIntArray(result_size_i);

    int* pint= new int[result_size_i];
    for(int i = 0 ;i < result_size_i;i++){
       pint[i] = result[i];
    }
    env->SetIntArrayRegion(finalResult,0,result_size_i, pint);
    delete pint;
    return finalResult;
}

JNIEXPORT jintArray JNICALL Java_co_darma_smartmattress_analysis_healthAlgorithms_basic_DMAlgorithmMattress_offlineBRCalculation  
(JNIEnv * env, jobject object, jintArray intarray)
{
    jsize length = env->GetArrayLength(intarray);
    jint* opticData = env->GetIntArrayElements(intarray, 0);

    vector<int> newVector;
    for(int i = 0 ;i < length;++i){
         newVector.push_back(opticData[i]);
    }

    env->ReleaseIntArrayElements(intarray,opticData,0);
    // call the alogrithm....

    vector<int> result = OfflineBRCalculation(newVector);
    //end....

    int result_size_i = result.size();
    jintArray finalResult = env->NewIntArray(result_size_i);

    int* pint= new int[result_size_i];
    for(int i = 0 ;i < result_size_i;i++){
       pint[i] = result[i];
    }
    env->SetIntArrayRegion(finalResult,0,result_size_i, pint);
    delete pint;
    return finalResult;
}


JNIEXPORT jintArray JNICALL Java_co_darma_smartmattress_analysis_healthAlgorithms_basic_DMAlgorithmMattress_offlineHRCalculation
(JNIEnv * env, jobject object, jintArray intarray)
{
    jsize length = env->GetArrayLength(intarray);
    jint* opticData = env->GetIntArrayElements(intarray, 0);

    vector<int> newVector;
    for(int i = 0 ;i < length;++i){
         newVector.push_back(opticData[i]);
    }

    env->ReleaseIntArrayElements(intarray,opticData,0);

    vector<int> result = OfflineHRCalculation(newVector);

    int result_size_i = result.size();
    jintArray finalResult = env->NewIntArray(result_size_i);

    int* pint= new int[result_size_i];
    for(int i = 0 ;i < result_size_i;i++){
       pint[i] = result[i];
    }
    env->SetIntArrayRegion(finalResult,0,result_size_i, pint);
    delete pint;
    return finalResult;

}

JNIEXPORT jintArray JNICALL Java_co_darma_smartmattress_analysis_healthAlgorithms_basic_DMAlgorithmMattress_sleepStageAnalysis
(JNIEnv * env, jobject object, jintArray intarray,jintArray heartRateArray, jintArray breathArray)
{
    jsize length = env->GetArrayLength(intarray);
    jint* opticData = env->GetIntArrayElements(intarray, 0);

    vector<int> newVector;
    for(int i = 0 ;i < length;++i){
         newVector.push_back(opticData[i]);
    }

    env->ReleaseIntArrayElements(intarray,opticData,0);


     jsize heartRateLength = env->GetArrayLength(heartRateArray);
     jint* heartData = env->GetIntArrayElements(heartRateArray, 0);
     
    jsize breathLength = env->GetArrayLength(breathArray);
     jint* brearthData = env->GetIntArrayElements(breathArray, 0);

     vector<int> heartVector;
     for(int i = 0 ;i < heartRateLength ;++i){
         heartVector.push_back(heartData[i]);
     }
    env->ReleaseIntArrayElements(heartRateArray,heartData,0);

     vector<int> breathVector;
     for(int i = 0 ;i < breathLength;++i){
         breathVector.push_back(brearthData[i]);
     }
    env->ReleaseIntArrayElements(breathArray,brearthData,0);

    vector<int> result = sleepStageAnalysis(newVector,heartVector,breathVector);

    int result_size_i = result.size();
    jintArray finalResult = env->NewIntArray(result_size_i);

    int* pint= new int[result_size_i];
    for(int i = 0 ;i < result_size_i;i++){
       pint[i] = result[i];
    }
    env->SetIntArrayRegion(finalResult,0,result_size_i, pint);
    delete pint;
    return finalResult;

}

package com.lakala.appcomponent.idCardOCRManager;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Parcelable;

import exocr.cardrec.CardInfo;
import exocr.cardrec.RecCardManager;
import exocr.cardrec.Status;
import exocr.engine.DataCallBack;
import exocr.engine.EngineManager;
import exocr.exocrengine.EXIDCardResult;

/**
 * Created by admin on 2018/11/20.
 */

public class IDCardRecognize implements DataCallBack {

    private final Activity activity;
    private final IIDCardRecognize iIDCardRecognize;

    public IDCardRecognize(Activity activity, IIDCardRecognize iIDCardRecognize) {
        this.activity = activity;
        this.iIDCardRecognize = iIDCardRecognize;

        EngineManager.getInstance().initEngine(activity);
    }


    public void startRecognize() {
        RecCardManager.getInstance().setScanMode(RecCardManager.scanMode.IMAGEMODE_HIGH, 0);
        RecCardManager.getInstance().setPackageName(activity.getPackageName());
        RecCardManager.getInstance().setShowLogo(false);
        RecCardManager.getInstance().setShowPhoto(true);
        RecCardManager.getInstance().recognize(this, activity, RecCardManager.cardType.EXOCRCardTypeIDCARD);
    }

    public void releaseRecognize() {
        RecCardManager.getInstance().pauseRecognizeWithStopStream(true);
        EngineManager.getInstance().finishEngine();
    }

    @Override
    public void onRecSuccess(Status status, CardInfo cardInfo) {

    }

    @Override
    public void onRecParticularSuccess(Status status, Parcelable parcelable) {
        if (iIDCardRecognize != null) {
            iIDCardRecognize.onCardDetected((EXIDCardResult) parcelable);
        }

        releaseRecognize();
    }

    @Override
    public void onRecCanceled(Status status) {

    }

    @Override
    public void onRecFailed(Status status, Bitmap bitmap) {
        if (iIDCardRecognize != null) {
            iIDCardRecognize.onFail(bitmap);
        }

        releaseRecognize();
    }

    @Override
    public void onCameraDenied() {

    }


    public interface IIDCardRecognize {
        void onCardDetected(EXIDCardResult exidCardResult);

        void onFail(Bitmap bitmap);
    }
}

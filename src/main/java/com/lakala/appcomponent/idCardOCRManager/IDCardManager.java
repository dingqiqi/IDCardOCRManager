package com.lakala.appcomponent.idCardOCRManager;

import android.app.Activity;

public class IDCardManager {

    /**
     * 身份证识别
     *
     * @param callback 回调
     */
    public void iDCardRecognize(Activity activity, IDCardRecognize.IIDCardRecognize callback) {
        if (activity == null) {
            if (callback != null) {
                callback.onFail(null);
                return;
            }
        }

        IDCardRecognize cardRecognize = new IDCardRecognize(activity, callback);
        cardRecognize.startRecognize();
    }
}

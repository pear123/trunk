package com.arvato.platform.security.captcha;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 4/10/14
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class AsyncCaptchaService extends GenericManageableCaptchaService {

    public AsyncCaptchaService(CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    @Override
    public java.lang.Boolean validateResponseForID(String ID, Object response) throws com.octo.captcha.service.CaptchaServiceException {
        if (!this.store.hasCaptcha(ID)) {
            throw new CaptchaServiceException("Invalid ID, could not validate unexisting or already validated captcha");
        }

        Boolean valid = this.store.getCaptcha(ID).validateResponse(response);
        //this.store.removeCaptcha(ID);
        return valid;
    }
}

package com.hp.vtms;

import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-12-16 Time: 5:31 To change
 * this template use File | Settings | File Templates.
 */
@Component
public class VCloudExceptionsHandler {

    public void customVcloudExceptions(String msg) throws BusinessException {

        VcloudExceptionEnum[] allVcloudException = VcloudExceptionEnum.values();

        for (VcloudExceptionEnum vcloudExceptionEnum : allVcloudException) {
            if (msg.equals(vcloudExceptionEnum.code))
                throw new BusinessException(vcloudExceptionEnum.errStr);
        }

    }
}

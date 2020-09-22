package org.zxy.new_manager.service;

import org.zxy.new_manager.dataobject.Api;
import org.zxy.new_manager.form.ApiForm;
import org.zxy.new_manager.form.ApiUpdateForm;

public interface ApiService {


    Api add(ApiForm apiForm);

    Api delete(Integer apiId);

    Api update(ApiUpdateForm apiUpdateForm);

}

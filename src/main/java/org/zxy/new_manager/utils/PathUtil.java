package org.zxy.new_manager.utils;

import lombok.Data;
import org.zxy.new_manager.enums.ResponseEnum;
import org.zxy.new_manager.exception.ApiException;

@Data
public class PathUtil {

    private String servlet;

    private String[] servletList;

    public PathUtil(String servlet) {
        this.servlet = servlet;
        this.servletList = this.servlet.split("/");
    }

    public String extractApp() {
        try{
            return servletList[2];
        }catch (ArrayIndexOutOfBoundsException e) {
            throw new ApiException(ResponseEnum.URL_PATH_ERROR);
        }

    }

    public String extractApi() {
        try{
            return servletList[3];
        }catch (ArrayIndexOutOfBoundsException e) {
            throw new ApiException(ResponseEnum.URL_PATH_ERROR);
        }
    }
}

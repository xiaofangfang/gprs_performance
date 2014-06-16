/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xllj.domain;

import java.util.Objects;

/**
 *
 * @author yuhaifang
 * 
 */
public class DeviceInfo {

    @Override
    public String toString() {
        return "DeviceInfo{" + "port=" + port + ", device_flag=" + device_flag + ", send_message=" + send_message + ", protecol=" + protecol + ", collection_frq=" + collection_frq + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeviceInfo other = (DeviceInfo) obj;
        if (!Objects.equals(this.device_flag, other.device_flag)) {
            return false;
        }
        return true;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDevice_flag() {
        return device_flag;
    }

    public void setDevice_flag(String device_flag) {
        this.device_flag = device_flag;
    }

    public String getSend_message() {
        return send_message;
    }

    public void setSend_message(String send_message) {
        this.send_message = send_message;
    }

    public String getProtecol() {
        return protecol;
    }

    public void setProtecol(String protecol) {
        this.protecol = protecol;
    }

    public int getCollection_frq() {
        return collection_frq;
    }

    public void setCollection_frq(int collection_frq) {
        this.collection_frq = collection_frq;
    }
      private int port; 
      private String device_flag; 
       private  String send_message;
      private  String protecol;
       private int collection_frq;
       
}

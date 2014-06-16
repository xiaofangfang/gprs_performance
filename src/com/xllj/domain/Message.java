/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xllj.domain;

/**
 *
 * @author yuhaifang
 */
public class Message {

    public Message(String hex, long addtime) {
        this.hex = hex;
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Message{" + "hex=" + hex + ", addtime=" + addtime + '}';
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }
    private String hex;
    private long addtime;
}

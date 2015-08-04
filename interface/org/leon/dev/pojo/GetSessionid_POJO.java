package org.leon.dev.pojo;


import org.leon.dev.pojo.inter.Object_POJO;

/**
 * Created by LeonWong on 15/6/23.
 */
public class GetSessionid_POJO implements Object_POJO {
    private String session_id ;
    private String p_key;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getP_key() {
        return p_key;
    }

    public void setP_key(String p_key) {
        this.p_key = p_key;
    }
}

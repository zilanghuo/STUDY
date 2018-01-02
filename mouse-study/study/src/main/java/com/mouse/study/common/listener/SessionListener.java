package com.mouse.study.common.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lwf
 * @date 2017/12/28
 * use:
 */
@Slf4j
public class SessionListener implements HttpSessionListener {

    private static HashMap hashUserName = new HashMap();//保存sessionID和username的映射
    /**以下是实现HttpSessionListener中的方法**/
    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent){
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent){
        hashUserName.remove(sessionEvent.getSession().getId());
    }

    /**
     * isLogining-用于判断用户是否已经登录
     * @param        sessionUserName String-登录的用户名
     * @return boolean-该用户是否已经登录的标志
     * */
    public static boolean isLogining(String sessionUserName) throws Exception{
        return hashUserName.containsValue(sessionUserName);
    }

    /*
    * isOnline-用于判断用户是否在线
    * @param session HttpSession-登录的用户名称
    * @return boolean-该用户是否在线的标志
    */
    public static boolean isOnline(HttpSession session) throws Exception{
        return hashUserName.containsKey(session.getId());
    }

    /*
    * createUserSession-用于建立用户session
    * @param sessionUserName String-登录的用户名称
    */
    public static void createUserSession(HttpSession session,String sessionUserName) throws Exception {

        hashUserName.put(session.getId(),sessionUserName);

        if(log.isDebugEnabled()){//log4j调试信息
            Iterator debugIter = hashUserName.entrySet().iterator();
            while (debugIter.hasNext()){
                Map.Entry entry = (Map.Entry)debugIter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                log.debug(sessionUserName);
                log.debug(key.toString());
                log.debug(val.toString());
            }
        }//log4j调试信息结束

    }

    /*
    * createUserSession-根据用户名剔除session
    * @param sessionUserName String-登录的用户名称
    */
    public static void removeUserSession(String sessionUserName) throws Exception{
        Iterator iter = hashUserName.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if(((String)val).equals(sessionUserName)){
                hashUserName.put(key,null);
                iter.remove();
                //hashUserName.remove(key);//当使用 fail-fast iterator 对 Collection 或 Map 进行迭代操作过程中尝试直接修改 Collection / Map 的内容时，即使是在单线程下运行,java.util.ConcurrentModificationException 异常也将被抛出。
            }
        }
    }

    /*
    * replaceUserSession-用户已经登录则进行session剔除,否则建立新的session
    * @param sUserName String-登录的用户名称
    */
    public static void replaceUserSession(HttpSession session,String sessionUserName) throws Exception {
        if(hashUserName.containsValue(sessionUserName)){//如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在hashUserName中)
//遍历原来的hashUserName，删除原用户名对应的sessionID(即删除原来的sessionID和username)
            Iterator iter = hashUserName.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry = (Map.Entry)iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                if(((String)val).equals(sessionUserName)){
                    hashUserName.put(key,null);
                    iter.remove();
                    //hashUserName.remove(key);//当使用 fail-fast iterator 对 Collection 或 Map 进行迭代操作过程中尝试直接修改 Collection / Map 的内容时，即使是在单线程下运行,java.util.ConcurrentModificationException 异常也将被抛出。
                }
            }
            hashUserName.put(session.getId(),sessionUserName);//添加现在的sessionID和username
        }else{//如果该用户没登录过，直接添加现在的sessionID和username
            hashUserName.put(session.getId(),sessionUserName);
        }

        if(log.isDebugEnabled()){//log4j调试信息
            Iterator debugIter = hashUserName.entrySet().iterator();
            while (debugIter.hasNext()){
                Map.Entry entry = (Map.Entry)debugIter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                log.debug(sessionUserName);
                log.debug(key.toString());
                log.debug(val.toString());
            }
        }//log4j调试信息结束

    }
}
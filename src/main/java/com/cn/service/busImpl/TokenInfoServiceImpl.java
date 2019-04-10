package com.cn.service.busImpl;

import com.cn.common.MsgAndCode;
import com.cn.common.Utils;
import com.cn.dao.interfaceDao.BaseEntityDao;
import com.cn.entiy.wechatTokenInfo;
import com.cn.service.busService.WechatInfoService;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;


@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class TokenInfoServiceImpl implements WechatInfoService {
    Logger logger = Logger.getLogger(TokenInfoServiceImpl.class);
    /*
       变量：码表dao实现
      */
    @Autowired
    private BaseEntityDao baseEntityDao;

    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 新增学校
     *
     * @param jsonstr
     * @return
     */
    public JSONObject insertToken(String jsonstr) {
        logger.info("新增token接口信息insertToken--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String appid = jo.getString("appid");//学校名称
        String secret = jo.getString("secret");//省名称
        String token = jo.getString("token");//市id
        String time = jo.getString("time");//县
        if (Utils.isEmpty(appid) || Utils.isEmpty(secret) || Utils.isEmpty(token) ||
                Utils.isEmpty(time)) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                Conjunction cn = Restrictions.conjunction();
                cn.add(Restrictions.eq("wechatAppid", appid));
                cn.add(Restrictions.eq("wechatSecret", secret));
                List<wechatTokenInfo> wechatTokenInfoList = baseEntityDao.listByCriteria(wechatTokenInfo.class, cn, false);
                wechatTokenInfo wechatTokenInfos = wechatTokenInfoList != null && wechatTokenInfoList.size() > 0 ? wechatTokenInfoList.get(0) : new wechatTokenInfo();
                wechatTokenInfos.setTokenCreateTime(sdfDate.parse(time));
                wechatTokenInfos.setWechatToken(token);
                wechatTokenInfos.setWechatAppid(appid);
                wechatTokenInfos.setWechatSecret(secret);
                String resultStr = baseEntityDao.saveOrUpdate(wechatTokenInfos);
                if (resultStr.equals("success")) {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
                } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_003);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_003_MSG);
                }
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
       功能：获取token
        */
    public JSONObject getToken(String jsonstr){
        logger.info("新增token接口信息insertToken--------" + jsonstr);
        JSONObject result = new JSONObject();
        JSONObject jo = new JSONObject(jsonstr);
        String appid = jo.getString("appid");//学校名称
        String secret = jo.getString("secret");//省名称
        if (Utils.isEmpty(appid) || Utils.isEmpty(secret) ) {
            result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_001);
            result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_001_MSG);
            return result;
        } else {
            try {
                //保存
                Conjunction cn = Restrictions.conjunction();
                cn.add(Restrictions.eq("wechatAppid", appid));
                cn.add(Restrictions.eq("wechatSecret", secret));
                List<wechatTokenInfo> wechatTokenInfoList = baseEntityDao.listByCriteria(wechatTokenInfo.class, cn, false);
               if(wechatTokenInfoList != null && wechatTokenInfoList.size() > 0){
                  result.put("time",wechatTokenInfoList.get(0).getTokenCreateTime());
                  result.put("token",wechatTokenInfoList.get(0).getWechatToken());
                   result.put(MsgAndCode.RSP_CODE, MsgAndCode.SUCCESS_CODE);
                   result.put(MsgAndCode.RSP_DESC, MsgAndCode.SUCCESS_MESSAGE);
               } else {
                    result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_008);
                    result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_008_MSG);
                }
            } catch (Exception e) {
                result.put(MsgAndCode.RSP_CODE, MsgAndCode.CODE_002);
                result.put(MsgAndCode.RSP_DESC, MsgAndCode.CODE_002_MSG);
                e.printStackTrace();
            }
        }
        return result;
    }
}

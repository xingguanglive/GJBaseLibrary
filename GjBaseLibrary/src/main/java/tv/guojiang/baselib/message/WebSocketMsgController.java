package com.efeizao.massage;

import com.efeizao.feizao.common.JacksonUtil;
import com.efeizao.feizao.live.model.OnBanBean;
import com.efeizao.feizao.live.model.OnBatchLoginBean;
import com.efeizao.feizao.live.model.OnBatchLogoutBean;
import com.efeizao.feizao.live.model.OnCancelVideoChatBean;
import com.efeizao.feizao.live.model.OnChangeVideoPullUrlBean;
import com.efeizao.feizao.live.model.OnHotRankBean;
import com.efeizao.feizao.live.model.OnInitRoomBean;
import com.efeizao.feizao.live.model.OnLoginBean;
import com.efeizao.feizao.live.model.OnLogoutBean;
import com.efeizao.feizao.live.model.OnMessageCardActiveBean;
import com.efeizao.feizao.live.model.OnModeratorLevelIncreaseBean;
import com.efeizao.feizao.live.model.OnNewBulleBarrageBean;
import com.efeizao.feizao.live.model.OnNewRewardsBean;
import com.efeizao.feizao.live.model.OnRefreshOnlineNumBean;
import com.efeizao.feizao.live.model.OnRoomMissionBean;
import com.efeizao.feizao.live.model.OnSendGifBean;
import com.efeizao.feizao.live.model.OnSendMsgBean;
import com.efeizao.feizao.live.model.OnSetAdminBean;
import com.efeizao.feizao.live.model.OnSystemMessageBean;
import com.efeizao.feizao.live.model.OnTiBean;
import com.efeizao.feizao.live.model.OnTiModeratorBean;
import com.efeizao.feizao.live.model.OnUnBanBean;
import com.efeizao.feizao.live.model.OnUnSetAdminBean;
import com.efeizao.feizao.live.model.OnUserAttentionBean;
import com.efeizao.feizao.live.model.OnUserLevelIncreasBean;
import com.efeizao.feizao.live.model.OnUserRejectVideoChatBean;
import com.efeizao.feizao.live.model.OnUserShareBean;
import com.efeizao.feizao.live.model.OnVideoChatEndBean;
import com.efeizao.feizao.model.Result;
import com.efeizao.feizao.model.ResultBean;
import com.efeizao.feizao.websocket.model.AcceptVideoChat;
import com.efeizao.feizao.websocket.model.InviteVideoChat;
import com.efeizao.feizao.websocket.model.VideoChat;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import cn.efeizao.feizao.framework.net.NetConstants;

/**
 * websocket收到消息后统一处理分发
 * author KK
 * date 2017/8/18
 */
public class WebSocketMsgController extends MessageContent {
    private static final String TAG = WebSocketMsgController.class.getName();

    private IMessageHelper mIMessageHelper;

    public WebSocketMsgController() {
        mIMessageHelper = MessageHelper.builder();
    }

    /**
     * 分发消息，根据消息cmd区分消息解析
     *
     * @param msg
     */
    public void dispatchMessage(String msg) {
        Result result = loadMessage(msg);

        if(!NetConstants.SENT_STATUS_SUCCESS.equals(result.errno)){
            sendMessage(result, msg);
            return;
        }
        if (ON_INIT_ROOM.equals(result.cmd)) {
            ResultBean<OnInitRoomBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnInitRoomBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_SEND_MSG.equals(result.cmd)) {//发送文本消息
            ResultBean<OnSendMsgBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnSendMsgBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_SEND_GIFT.equals(result.cmd)) {//送礼
            ResultBean<OnSendGifBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnSendGifBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_SEND_FLOWER.equals(result.cmd)) {//送花
            sendMessage(result, msg);
        } else if (ON_USER_ATTENTION.equals(result.cmd)) {//用户关注主播上报
            ResultBean<OnUserAttentionBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnUserAttentionBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_USER_SHARE.equals(result.cmd)) {//用户分享直播间上报
            ResultBean<OnUserShareBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnUserShareBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_LOGIN.equals(result.cmd)) {//进入直播间回调
            ResultBean<OnLoginBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnLoginBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_LOGOUT.equals(result.cmd)) {//退出
            ResultBean<OnLogoutBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnLogoutBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_VIDEO_PUBLISH.equals(result.cmd)) {//上报直播开始

            sendMessage(result, msg);
        } else if (ON_VIDEO_UNPUBLISH.equals(result.cmd)) {//上报直播结束
            sendMessage(result, msg);
        } else if (ON_BAN.equals(result.cmd)) {//禁言
            ResultBean<OnBanBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnBanBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_UN_BAN.equals(result.cmd)) {//解除禁言
            ResultBean<OnUnBanBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnUnBanBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_SET_ADMIN.equals(result.cmd)) {//设为管理通知
            ResultBean<OnSetAdminBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnSetAdminBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_UNSET_ADMIN.equals(result.cmd)) {//取消管理通知
            ResultBean<OnUnSetAdminBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnUnSetAdminBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_TI.equals(result.cmd)) {//踢人
            ResultBean<OnTiBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnTiBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_TI_MODERATOR.equals(result.cmd)) {
            ResultBean<OnTiModeratorBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnTiModeratorBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_BATCH_LOGIN.equals(result.cmd)) {// 僵死用户进入房间
            ResultBean<OnBatchLoginBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnBatchLoginBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_BATCH_LOGOUT.equals(result.cmd)) {// 僵死用户退出房间
            ResultBean<OnBatchLogoutBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnBatchLogoutBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_NEW_BULLE_BARRAGE.equals(result.cmd)) {// 消息弹幕
            ResultBean<OnNewBulleBarrageBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnNewBulleBarrageBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_NEW_REWARDS.equals(result.cmd)) {// 开宝箱
            ResultBean<OnNewRewardsBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnNewRewardsBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_REFRESH_ONLINE_NUM.equals(result.cmd)) {//更新在线人数
            ResultBean<OnRefreshOnlineNumBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnRefreshOnlineNumBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_MODERATOR_LEVEL_INCREASE.equals(result.cmd)) {//主播升级
            ResultBean<OnModeratorLevelIncreaseBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnModeratorLevelIncreaseBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_USER_LEVEL_INCREASE.equals(result.cmd)) {//用户升级
            ResultBean<OnUserLevelIncreasBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnUserLevelIncreasBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_SYSTEM_MESSAGE.equals(result.cmd)) {//用户升级
            ResultBean<OnSystemMessageBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnSystemMessageBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_HOT_RANK.equals(result.cmd)) {//热门排名
            ResultBean<OnHotRankBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnHotRankBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_MESSAGE_CARD_ACTIVE.equals(result.cmd)) {//开通私信卡
            ResultBean<OnMessageCardActiveBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnMessageCardActiveBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_INVITE_VIDEO_CHAT.equals(result.cmd)) {// 邀请连线-------
            ResultBean<InviteVideoChat> bean = loadMessage(msg, new TypeToken<ResultBean<InviteVideoChat>>(){});
            sendMessage(bean, msg);
        } else if (ON_CANCEL_VIDEO_CHAT.equals(result.cmd)) {// 取消连线
            ResultBean<OnCancelVideoChatBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnCancelVideoChatBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_USER_REJECT_VIDEO_CHAT.equals(result.cmd)) {//用户拒绝主播连线
            ResultBean<OnUserRejectVideoChatBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnUserRejectVideoChatBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_ACCEPT_VIDEO_CHAT.equals(result.cmd)) {//接受连线--------
            ResultBean<AcceptVideoChat> bean = loadMessage(msg, new TypeToken<ResultBean<AcceptVideoChat>>(){});
            sendMessage(bean, msg);
        } else if (ON_VIDEO_CHAT_END.equals(result.cmd)) {//连线结束（一方关闭或者断线）
            ResultBean<OnVideoChatEndBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnVideoChatEndBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_CHANGE_VIDEO_PULL_URL.equals(result.cmd)) { //主播推流切换观众需要更换拉流地址

            ResultBean<OnChangeVideoPullUrlBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnChangeVideoPullUrlBean>>(){});
            sendMessage(bean, msg);
        } else if (ON_VIDEO_CHAT.equals(result.cmd)) { //连麦成功广播连麦用户信息-------
            ResultBean<VideoChat> bean = loadMessage(msg, new TypeToken<ResultBean<VideoChat>>() {
            });
            sendMessage(bean, msg);
        } else if (ON_ROOM_MISSION.equals(result.cmd)) { //任务推送信息
            ResultBean<OnRoomMissionBean> bean = loadMessage(msg, new TypeToken<ResultBean<OnRoomMissionBean>>() {
            });
            sendMessage(bean, msg);
        }

    }

    public void sendMessage(Result result, String msg) {
        mIMessageHelper.post(new MessageInfo(result.cmd, msg, result));
    }

    /**
     * 发送消息
     *
     * @param result
     * @param msg
     */
    public void sendMessage(ResultBean result, String msg) {
        mIMessageHelper.post(new MessageInfo(result.cmd, msg, result));
    }

    /**
     * 解析消息实体
     *
     * @param msg
     * @param c
     * @param <T>
     * @return
     */
    public <T> ResultBean<T> loadMessage(String msg, ResultBean<T> c) {
        JacksonUtil gson = new JacksonUtil();
        Type type = new TypeToken<ResultBean<T>>() {
        }.getType();
        return gson.readValue(msg, type);
    }

    public <T> ResultBean<T> loadMessage(String msg, TypeToken<ResultBean<T>> c) {
        JacksonUtil gson = new JacksonUtil();
//        Type type = new TypeToken<ResultBean<T>>() {
//        }.getType();
        return gson.readValue(msg, c.getType());
    }




    public <T> ResultBean<T> loadMessage(String msg, Class<?>... c) {
        JacksonUtil gson = new JacksonUtil();
        try {
            return gson.readValue(msg, ResultBean.class, c);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public Result loadMessage(String msg) {
        JacksonUtil gson = new JacksonUtil();
        try {
            return gson.readValue(msg, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

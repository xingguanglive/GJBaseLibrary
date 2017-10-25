package tv.guojiang.baselib.message;

/**
 * author KK
 * date 2017/8/18
 */

public class MessageContent {

    public static final String ON_INIT_ROOM = "onInitRoom";
    public static final String ON_SEND_FLOWER = "onSendFlower";
    public static final String ON_FIRST_SEND_FLOWER = "onFirstSendFlower";
    public static final String ON_USER_ATTENTION = "onUserAttention";
    public static final String ON_USER_SHARE = "onUserShare";
    public static final String ON_LOGIN = "onLogin";
    public static final String ON_LOGOUT = "onLogout";
    public static final String ON_SEND_MSG = "onSendMsg";
    public static final String ON_SEND_GIFT = "onSendGift";
    public static final String ON_VIDEO_PUBLISH = "onVideoPublish";
    public static final String ON_VIDEO_UNPUBLISH = "onVideoUnpublish";
    public static final String ON_CONNECT_STATUS = "onConnectStatus";
    public static final String ON_BAN = "onBan";
    public static final String ON_UN_BAN = "onUnBan";
    public static final String ON_SET_ADMIN = "onSetAdmin";
    public static final String ON_UNSET_ADMIN = "onUnsetAdmin";
    public static final String ON_TI = "onTi";
    public static final String ON_TI_MODERATOR = "onTiModerator";
    public static final String ON_BATCH_LOGIN = "onBatchLogin";
    public static final String ON_BATCH_LOGOUT = "onBatchLogout";
    public static final String ON_NEW_BULLE_BARRAGE = "onNewBulletBarrage";
    public static final String ON_NEW_REWARDS = "onNewRewards";
    public static final String ON_REFRESH_ONLINE_NUM = "onRefreshOnlineNum";
    public static final String ON_MODERATOR_LEVEL_INCREASE = "onModeratorLevelIncrease";
    public static final String ON_USER_LEVEL_INCREASE = "onUserLevelIncrease";
    public static final String ON_SEND_BARRAGE = "onSendBarrage";
    public static final String ON_SYSTEM_MESSAGE = "onSystemMsg";
    public static final String ON_HOT_RANK = "onNewHotRank";            //排名变化的通知
    public static final String ON_MESSAGE_CARD_ACTIVE = "onMessageCardActive";
    public static final String ON_INVITE_VIDEO_CHAT = "onInviteVideoChat";        // 邀请连线
    public static final String ON_CANCEL_VIDEO_CHAT = "onCancelVideoChat";        // 取消连线
    public static final String ON_USER_REJECT_VIDEO_CHAT = "onUserRejectVideoChat";    //用户拒绝主播连线
    public static final String ON_ACCEPT_VIDEO_CHAT = "onAcceptVideoChat";    //接受连线
    public static final String ON_VIDEO_CHAT_END = "onVideoChatEnd";    //连线结束（混流失败，用户关闭，用户断线，主播断线都会发出此命令）
    public static final String ON_CHANGE_VIDEO_PULL_URL = "onUpdateModeratorPullUrl";    //观众更换拉流地址
    public static final String ON_VIDEO_CHAT = "onVideoChat";    //连麦成功广播连麦用户信息
    public static final String ON_ROOM_MISSION = "onRoomMission"; //众筹任务


}

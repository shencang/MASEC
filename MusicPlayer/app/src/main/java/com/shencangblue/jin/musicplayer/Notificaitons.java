package com.shencangblue.jin.musicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.widget.RemoteViews;

public class Notificaitons {
    public final static int NOTIFICATION_SAMPLE = 0;
    public final static int NOTIFICATION_ACTION = 1;
    public final static int NOTIFICATION_REMOTE_INPUT = 2;
    public final static int NOTIFICATION_BIG_PICTURE_STYLE = 3;
    public final static int NOTIFICATION_BIG_TEXT_STYLE = 4;
    public final static int NOTIFICATION_INBOX_STYLE = 5;
    public final static int NOTIFICATION_MEDIA_STYLE = 6;
    public final static int NOTIFICATION_MESSAGING_STYLE = 7;
    public final static int NOTIFICATION_PROGRESS = 8;
    public final static int NOTIFICATION_CUSTOM_HEADS_UP = 9;
    public final static int NOTIFICATION_CUSTOM = 10;

    public final static String ACTION_SIMPLE = "com.shencangblue.jin.musicplayer.ACTION_SIMPLE";
    public final static String ACTION_ACTION = "com.shencangblue.jin.musicplayer.ACTION_ACTION";
    public final static String ACTION_REMOTE_INPUT = "com.shencangblue.jin.musicplayer.ACTION_REMOTE_INPUT";
    public final static String ACTION_BIG_PICTURE_STYLE = "com.shencangblue.jin.musicplayer.ACTION_BIG_PICTURE_STYLE";
    public final static String ACTION_BIG_TEXT_STYLE = "com.shencangblue.jin.musicplayer.ACTION_BIG_TEXT_STYLE";
    public final static String ACTION_INBOX_STYLE = "com.shencangblue.jin.musicplayer.ACTION_INBOX_STYLE";
    public final static String ACTION_MEDIA_STYLE = "com.shencangblue.jin.musicplayer.ACTION_MEDIA_STYLE";
    public final static String ACTION_MESSAGING_STYLE = "com.shencangblue.jin.musicplayer.ACTION_MESSAGING_STYLE";
    public final static String ACTION_PROGRESS = "com.shencangblue.jin.nmusicplayer.ACTION_PROGRESS";
    public final static String ACTION_CUSTOM_HEADS_UP_VIEW = "com.shencangblue.jin.musicplayer.ACTION_CUSTOM_HEADS_UP_VIEW";
    public final static String ACTION_CUSTOM_VIEW = "com.shencangblue.jin.musicplayer.ACTION_CUSTOM_VIEW";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_LOVE = "com.shencangblue.jin.musicplayer.ACTION_CUSTOM_VIEW_OPTIONS_LOVE";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_PRE = "com.shencangblue.jin.musicplayer.ACTION_CUSTOM_VIEW_OPTIONS_PRE";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE = "com.shencangblue.jin.musicplayer.ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_NEXT = "com.shencangblue.jin.musicplayer.ACTION_CUSTOM_VIEW_OPTIONS_NEXT";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_LYRICS = "com.shencangblue.jin.musicplayer.ACTION_CUSTOM_VIEW_OPTIONS_LYRICS";
    public final static String ACTION_CUSTOM_VIEW_OPTIONS_CANCEL = "com.shencangblue.jin.musicplayer.ACTION_CUSTOM_VIEW_OPTIONS_CANCEL";

    public final static String ACTION_YES = "com.shencangblue.jin.musicplayer.ACTION_YES";
    public final static String ACTION_NO = "com.shencangblue.jin.musicplayer.ACTION_NO";
    public final static String ACTION_DELETE = "com.shencangblue.jin.musicplayer.ACTION_DELETE";
    public final static String ACTION_REPLY = "com.shencangblue.jin.musicplayer.ACTION_REPLY";
    public final static String REMOTE_INPUT_RESULT_KEY = "remote_input_content";

    public final static String EXTRA_OPTIONS = "options";
    public final static String MEDIA_STYLE_ACTION_DELETE = "action_delete";
    public final static String MEDIA_STYLE_ACTION_PLAY = "action_play";
    public final static String MEDIA_STYLE_ACTION_PAUSE = "action_pause";
    public final static String MEDIA_STYLE_ACTION_NEXT = "action_next";
    public final static String ACTION_ANSWER = "action_answer";
    public final static String ACTION_REJECT = "action_reject";


    private static volatile Notificaitons sInstance = null;


    private Notificaitons() {
    }

    public static Notificaitons getInstance() {
        if (sInstance == null) {
            synchronized (Notificaitons.class) {
                if (sInstance == null) {
                    sInstance = new Notificaitons();
                }
            }
        }

        return sInstance;
    }

    public void sendSimpleNotification(Context context, NotificationManager nm) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_SIMPLE);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        //创建删除通知时发送的广播
        Intent deleteIntent = new Intent(context, NotificationService.class);
        deleteIntent.setAction(ACTION_DELETE);
        PendingIntent deletePendingIntent = PendingIntent.getService(context, 0, deleteIntent, 0);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.LOW)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("简单通知")
                //设置通知内容
                .setContentText("简单通知演示")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置通知右侧的大图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_notifiation_big))
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置删除通知时的响应事件
                .setDeleteIntent(deletePendingIntent);
        //发送通知
        nm.notify(NOTIFICATION_SAMPLE, nb.build());
    }

    public void sendActionNotification(Context context, NotificationManager nm) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_ACTION);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.DEFAULT)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("带Action按钮通知")
                //设置通知内容
                .setContentText("带Action按钮通知演示")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi);
        //创建点击通知 YES 按钮时发送的广播
        Intent yesIntent = new Intent(context, NotificationService.class);
        yesIntent.setAction(ACTION_YES);
        PendingIntent yesPendingIntent = PendingIntent.getService(context, 0, yesIntent, 0);
        Notification.Action yesActionBuilder = new Notification.Action.Builder(
                Icon.createWithResource("", R.mipmap.ic_yes),
                "确定",
                yesPendingIntent)
                .build();
        //创建点击通知 NO 按钮时发送的广播
        Intent noIntent = new Intent(context, NotificationService.class);
        noIntent.setAction(ACTION_NO);
        PendingIntent noPendingIntent = PendingIntent.getService(context, 0, noIntent, 0);
        Notification.Action noActionBuilder = new Notification.Action.Builder(
                Icon.createWithResource("", R.mipmap.ic_no),
                "取消",
                noPendingIntent)
                .build();
        //为通知添加按钮
        nb.setActions(yesActionBuilder, noActionBuilder);
        //发送通知
        nm.notify(NOTIFICATION_ACTION, nb.build());
    }

    public void sendRemoteInputNotification(Context context, NotificationManager nm) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_REMOTE_INPUT);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.IMPORTANCE)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("带快速回复功能通知")
                //设置通知内容
                .setContentText("带快速回复功能通知演示")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi);
        //创建带输入框的按钮
        RemoteInput remoteInput = new RemoteInput.Builder(REMOTE_INPUT_RESULT_KEY)
                .setLabel("回复").build();
        Intent remoteInputIntent = new Intent(context, NotificationService.class);
        remoteInputIntent.setAction(ACTION_REPLY);
        PendingIntent replyPendingIntent = PendingIntent.getService(context, 2, remoteInputIntent, 0);
        Notification.Action replyAction = new Notification.Action.Builder(
                Icon.createWithResource("", R.mipmap.ic_reply),
                "回复",
                replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build();
        //为通知添加按钮
        nb.setActions(replyAction);
        //发送通知
        nm.notify(NOTIFICATION_REMOTE_INPUT, nb.build());
    }

    public void sendBigPictureStyleNotification(Context context, NotificationManager nm) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_BIG_PICTURE_STYLE);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        //创建大视图样式
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle()
                .setBigContentTitle("大图效果通知 ~ 展开标题")
                .setSummaryText("大图效果通知 ~ 展开摘要")
                .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.mipmap.big_style_picture));
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.DEFAULT)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("大图效果通知")
                //设置通知内容
                .setContentText("大图效果通知演示")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置大视图样式通知
                .setStyle(bigPictureStyle);
        //发送通知
        nm.notify(NOTIFICATION_BIG_PICTURE_STYLE, nb.build());
    }

    public void sendBigTextStyleNotification(Context context, NotificationManager nm) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_BIG_TEXT_STYLE);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        //创建大文字样式
        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle()
                .setBigContentTitle("多文字效果通知 ~ 展开标题")
                .setSummaryText("多文字效果通知演示 ~ 展开摘要")
                .bigText("俺们是冠军   \n" +
                        "俺们是冠军   \n" +
                        "没有成为失败者   \n" +
                        "因为我们是世界冠军");
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.DEFAULT)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("多文字效果通知")
                //设置通知内容
                .setContentText("多文字效果通知演示 !")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置大文字样式通知
                .setStyle(bigTextStyle);
        //发送通知
        nm.notify(NOTIFICATION_BIG_TEXT_STYLE, nb.build());
    }

    public void sendInboxStyleNotification(Context context, NotificationManager nm) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_INBOX_STYLE);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        //创建信箱样式
        Notification.InboxStyle inboxStyle = new Notification.InboxStyle()
                .setBigContentTitle("信箱效果通知 ~ 展开标题")
                .setSummaryText("信箱效果通知演示 ! ~ 展开摘要")
                //最多六行
                .addLine("1. 电子邮件内容")
                .addLine("2. 电子邮件内容")
                .addLine("3. 电子邮件内容")
                .addLine("4. 电子邮件内容")
                .addLine("5. 电子邮件内容")
                .addLine("6. 电子邮件内容");
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.DEFAULT)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("信箱效果通知")
                //设置通知内容
                .setContentText("信箱效果通知演示")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置信箱样式通知
                .setStyle(inboxStyle);
        //发送通知
        nm.notify(NOTIFICATION_INBOX_STYLE, nb.build());
    }

    public void sendMediaStyleNotification(Context context, NotificationManager nm, boolean isPlaying) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_MEDIA_STYLE);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        //创建Action按钮
        Intent playOrPauseIntent = new Intent(context, NotificationService.class);
        playOrPauseIntent.setAction(ACTION_MEDIA_STYLE);
        playOrPauseIntent.putExtra(EXTRA_OPTIONS, isPlaying ? MEDIA_STYLE_ACTION_PAUSE : MEDIA_STYLE_ACTION_PLAY);
        PendingIntent playOrPausePendingIntent = PendingIntent.getService(context, 0, playOrPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action playOrPauseAction = new Notification.Action.Builder(
                Icon.createWithResource(context, isPlaying ? R.mipmap.ic_pause : R.mipmap.ic_play),
                isPlaying ? "PAUSE" : "PLAY",
                playOrPausePendingIntent)
                .build();
        Intent nextIntent = new Intent(context, NotificationService.class);
        nextIntent.setAction(ACTION_MEDIA_STYLE);
        nextIntent.putExtra(EXTRA_OPTIONS, MEDIA_STYLE_ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, 1, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action nextAction = new Notification.Action.Builder(
                Icon.createWithResource(context, R.mipmap.ic_next),
                "Next",
                nextPendingIntent)
                .build();
        Intent deleteIntent = new Intent(context, NotificationService.class);
        deleteIntent.setAction(ACTION_MEDIA_STYLE);
        deleteIntent.putExtra(EXTRA_OPTIONS, MEDIA_STYLE_ACTION_DELETE);
        PendingIntent deletePendingIntent = PendingIntent.getService(context, 2, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Action deleteAction = new Notification.Action.Builder(
                Icon.createWithResource(context, R.mipmap.ic_delete),
                "Delete",
                deletePendingIntent)
                .build();
        //创建媒体样式
        Notification.MediaStyle mediaStyle = new Notification.MediaStyle()
                //最多三个Action
                .setShowActionsInCompactView(0, 1, 2);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.MEDIA)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("媒体效果通知")
                //设置通知内容
                .setContentText("媒体效果通知演示")
                //设置通知不可删除
                .setOngoing(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置Action按钮
                .setActions(playOrPauseAction, nextAction, deleteAction)
                //设置信箱样式通知
                .setStyle(mediaStyle);

        //发送通知
        nm.notify(NOTIFICATION_MEDIA_STYLE, nb.build());
    }

    public void sendMessagingStyleNotification(Context context, NotificationManager nm) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_MESSAGING_STYLE);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        //创建信息样式
        Notification.MessagingStyle messagingStyle = new Notification.MessagingStyle("shencang")
                .setConversationTitle("信息效果通知")
                .addMessage("这是给你的消息", System.currentTimeMillis(), "shencang");
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.DEFAULT)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("信息效果通知")
                //设置通知内容
                .setContentText("信息效果通知演示")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置信箱样式通知
                .setStyle(messagingStyle);
        //发送通知
        nm.notify(NOTIFICATION_MESSAGING_STYLE, nb.build());
    }

    public void sendProgressViewNotification(Context context, NotificationManager nm, int progress) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_PROGRESS);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.LOW)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("下载中...")
                //设置通知内容
                .setContentText(String.valueOf(progress) + "%")
                //设置通知不可删除
                .setOngoing(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                .setProgress(100, progress, false);
        //发送通知
        nm.notify(NOTIFICATION_PROGRESS, nb.build());
    }

    public void sendCustomHeadsUpViewNotification(Context context, NotificationManager nm) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, LaunchActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        //创建自定义顶部提醒视图
        Intent answerIntent = new Intent(context, NotificationService.class);
        answerIntent.setAction(ACTION_CUSTOM_HEADS_UP_VIEW);
        answerIntent.putExtra(EXTRA_OPTIONS, ACTION_ANSWER);
        PendingIntent answerPendingIntent = PendingIntent.getService(context, 0, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent rejectIntent = new Intent(context, NotificationService.class);
        rejectIntent.setAction(ACTION_CUSTOM_HEADS_UP_VIEW);
        rejectIntent.putExtra(EXTRA_OPTIONS, ACTION_REJECT);
        PendingIntent rejectPendingIntent = PendingIntent.getService(context, 1, rejectIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews headsUpView = new RemoteViews(context.getPackageName(), R.layout.custom_heads_up_layout);
        headsUpView.setOnClickPendingIntent(R.id.iv_answer, answerPendingIntent);
        headsUpView.setOnClickPendingIntent(R.id.iv_reject, rejectPendingIntent);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.CRITICAL)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("自定义顶部提醒视图通知")
                //设置通知内容
                .setContentText("自定义顶部提醒视图通知演示")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置全屏响应事件;
                .setFullScreenIntent(pi, true)
                //设置自定义顶部提醒视图
                .setCustomHeadsUpContentView(headsUpView);
        //发送通知
        nm.notify(NOTIFICATION_CUSTOM_HEADS_UP, nb.build());
    }

    public void sendCustomViewNotification(Context context, NotificationManager nm, NotificationContentWrapper content, Boolean isLoved, Boolean isPlaying) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, LaunchActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        //创建各个按钮的点击响应广播
        Intent intentLove = new Intent(context, NotificationService.class);
        intentLove.setAction(ACTION_CUSTOM_VIEW_OPTIONS_LOVE);
        PendingIntent piLove = PendingIntent.getService(context, 0, intentLove, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentPre = new Intent(context, NotificationService.class);
        intentPre.setAction(ACTION_CUSTOM_VIEW_OPTIONS_PRE);
        PendingIntent piPre = PendingIntent.getService(context, 0, intentPre, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentPlayOrPause = new Intent(context, NotificationService.class);
        intentPlayOrPause.setAction(ACTION_CUSTOM_VIEW_OPTIONS_PLAY_OR_PAUSE);
        PendingIntent piPlayOrPause = PendingIntent.getService(context, 0, intentPlayOrPause, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentNext = new Intent(context, NotificationService.class);
        intentNext.setAction(ACTION_CUSTOM_VIEW_OPTIONS_NEXT);
        PendingIntent piNext = PendingIntent.getService(context, 0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentLyrics = new Intent(context, NotificationService.class);
        intentLyrics.setAction(ACTION_CUSTOM_VIEW_OPTIONS_LYRICS);
        PendingIntent piLyrics = PendingIntent.getService(context, 0, intentLyrics, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentCancel = new Intent(context, NotificationService.class);
        intentCancel.setAction(ACTION_CUSTOM_VIEW_OPTIONS_CANCEL);
        PendingIntent piCancel = PendingIntent.getService(context, 0, intentCancel, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建自定义小视图
        RemoteViews customView = new RemoteViews(context.getPackageName(), R.layout.custom_view_layout);
        customView.setImageViewBitmap(R.id.iv_content, content.bitmap);
        customView.setTextViewText(R.id.tv_title, content.title);
        customView.setTextViewText(R.id.tv_summery, content.summery);
        customView.setImageViewBitmap(R.id.iv_play_or_pause, BitmapFactory.decodeResource(context.getResources(),
                isPlaying ? R.mipmap.ic_pause : R.mipmap.ic_play));
        customView.setOnClickPendingIntent(R.id.iv_play_or_pause, piPlayOrPause);
        customView.setOnClickPendingIntent(R.id.iv_next, piNext);
        customView.setOnClickPendingIntent(R.id.iv_lyrics, piLyrics);
        customView.setOnClickPendingIntent(R.id.iv_cancel, piCancel);
        //创建自定义大视图
        RemoteViews customBigView = new RemoteViews(context.getPackageName(), R.layout.custom_big_view_layout);
        customBigView.setImageViewBitmap(R.id.iv_content_big, content.bitmap);
        customBigView.setTextViewText(R.id.tv_title_big, content.title);
        customBigView.setTextViewText(R.id.tv_summery_big, content.summery);
        customBigView.setImageViewBitmap(R.id.iv_love_big, BitmapFactory.decodeResource(context.getResources(),
                isLoved ? R.mipmap.ic_loved : R.mipmap.ic_love));
        customBigView.setImageViewBitmap(R.id.iv_play_or_pause_big, BitmapFactory.decodeResource(context.getResources(),
                isPlaying ? R.mipmap.ic_pause : R.mipmap.ic_play));
        customBigView.setOnClickPendingIntent(R.id.iv_love_big, piLove);
        customBigView.setOnClickPendingIntent(R.id.iv_pre_big, piPre);
        customBigView.setOnClickPendingIntent(R.id.iv_play_or_pause_big, piPlayOrPause);
        customBigView.setOnClickPendingIntent(R.id.iv_next_big, piNext);
        customBigView.setOnClickPendingIntent(R.id.iv_lyrics_big, piLyrics);
        customBigView.setOnClickPendingIntent(R.id.iv_cancel_big, piCancel);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.MEDIA)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_notification)
                //设置通知标题
                .setContentTitle("自定义视图通知")
                //设置通知内容
                .setContentText("自定义视图通知演示")
                //设置通知不可删除
                .setOngoing(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置自定义小视图
                .setCustomContentView(customView)
                //设置自定义大视图
                .setCustomBigContentView(customBigView);
        //发送通知
        nm.notify(NOTIFICATION_CUSTOM, nb.build());
    }

    public void clearAllNotification(NotificationManager nm) {
        nm.cancelAll();
    }
}
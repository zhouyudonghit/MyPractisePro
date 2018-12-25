package com.example.localuser.retrofittest.SuningPushTets;


public class MessageCenterManger implements IMessageCenterManager{
    public volatile static MessageCenterManger instance = null;
    private MessageService mMessageSerivce;
    private MessageWorker mWorker;

    private MessageCenterManger(MessageService servie)
    {
        mMessageSerivce = servie;
        mWorker = new MessageWorker(servie);
    }

    public static MessageCenterManger getInstance(MessageService service) {
        if(instance == null)
        {
            synchronized (MessageCenterManger.class)
            {
                if(instance == null)
                {
                    instance = new MessageCenterManger(service);
                }
            }
        }
        return instance;
    }

    @Override
    public void startMessageWorker() {
        mWorker.startWork();
    }

    @Override
    public void stopMessageWorker() {
        mWorker.stopWork();
    }
}

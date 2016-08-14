package uiComponents;

import uiComponents.pages.EntrancePage;
import uiComponents.pages.MainFrame;

/**
 * Created by Mactabi on 14/08/2016.
 */
public class TimerPanel {
    private int spentTime;
    public TimerPanel(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                spentTime++;
                if(spentTime==30)
                    MainFrame.getInstance().showPanel(EntrancePage.getInstance().getPanelIndex());
            }

        }).start();






    }
    void restart(){
        spentTime=0;
    }


}

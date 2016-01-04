package org.gosky.paradise.refreshlayout;

/**
 * @author galaxy captain
 * @date 2015/12/28
 */
public interface IRefreshState {

    /**
     * 普通状态
     */
    int NOMARL_STATE = 0;

    /**
     * 可执行状态
     */
    int EXEC_STATE = 1;

    boolean canPullDown();

    boolean canPullUp();

}

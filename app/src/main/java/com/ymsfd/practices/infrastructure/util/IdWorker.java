package com.ymsfd.practices.infrastructure.util;

import java.util.Locale;

/**
 * Description:
 * SnowFlake的结构如下(每部分用-分开):
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 1位标识, 由于 long 基本类型在 Java 中是带符号的, 最高位是符号位, 正数是0, 负数是1, 所以 id 一般是正数, 最高位是0
 * 41位时间截(毫秒级), 注意, 41位时间截不是存储当前时间的时间截, 而是存储时间截的差值(当前时间截 - 开始时间截),
 * 这里的的开始时间截, 一般是我们的 id 生成器开始使用的时间, 由我们程序来指定的(如下下面程序 IdWorker 类的 mEpoch 属性).
 * 41位的时间截, 可以使用69年, 年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69
 * 10位的数据机器位, 可以部署在1024个节点, 包括5位 DataCenterId 和5位 workerId
 * 12位序列, 毫秒内的计数, 12位的计数顺序号支持每个节点每毫秒(同一机器, 同一时间截)产生4096个ID序号加起来刚好64位, 为一个Long型。
 * SnowFlake 的优点是, 整体上按照时间自增排序, 并且整个分布式系统内不会产生 ID 碰撞(由数据中心 ID 和机器 ID 作区分), 并且效率较高, 经测试, SnowFlake 每秒能够产生26万 ID 左右。
 * Author: WoodenTea
 * Date: 2017/6/28
 */
public class IdWorker {
    private final Long mEpoch = 1498643890000L;
    private final int mWorkerIdBits = 5;
    private final int mDataCenterBits = 5;
    private final int mMaxWorkerId = ~(-1 << mWorkerIdBits);
    private final int mMaxDataCenterId = ~(-1 << mDataCenterBits);
    private final int mSequenceBits = 12;
    private final int mSequenceMask = ~(-1 << mSequenceBits);
    private final int mWorkerIdShift = mSequenceBits;
    private final int mDataCenterIdShift = mSequenceBits + mWorkerIdBits;
    private final int mTimestampIdShift = mSequenceBits + mWorkerIdBits + mDataCenterBits;

    private int mWorkerId;
    private int mDataCenterId;
    private int mSequence = 0;
    private Long mLastTimestamp = -1L;

    public IdWorker(int dataCenterId, int workId) {
        if (workId > mMaxWorkerId || workId < 0) {
            throw new IllegalArgumentException(String.format(Locale.getDefault(),
                    "worker Id can't be greater than %d or less than 0", mMaxWorkerId));
        }

        if (dataCenterId > mMaxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format(Locale.getDefault(),
                    "data center Id can't be greater than %d or less than 0", mMaxDataCenterId));
        }

        mWorkerId = workId;
        mDataCenterId = dataCenterId;
    }

    public synchronized Long nextId() {
        long timestamp = timeGen();

        if (timestamp < mLastTimestamp) {
            throw new RuntimeException(String.format(Locale.getDefault(),
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    mLastTimestamp - timestamp));
        }

        if (mLastTimestamp == timestamp) {
            mSequence = (mSequence + 1) & mSequenceMask;
            if (mSequence == 0) {
                timestamp = tilNextMillis(mLastTimestamp);
            }
        } else {
            mSequence = 0;
        }

        mLastTimestamp = timestamp;

        return ((timestamp - mEpoch) << mTimestampIdShift)
                | (mDataCenterId << mDataCenterIdShift)
                | (mWorkerId << mWorkerIdShift)
                | mSequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}

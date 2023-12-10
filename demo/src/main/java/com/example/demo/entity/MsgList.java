package com.example.demo.entity;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgList {
    private Long msgId;
    private Long userId;
    private String content;
    private Timestamp time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MsgList)) return false;
        MsgList msgList = (MsgList) o;
        return Objects.equals(msgId, msgList.msgId) &&
                Objects.equals( userId, msgList. userId) &&
                Objects.equals(content, msgList.content) &&
                Objects.equals(time, msgList.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgId, userId, content, time);
    }

    @Override
    public String toString() {
        return "MsgList{" +
                "msgId=" + msgId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }

}

package com.service;

import com.entity.Notice;
import java.util.List;

public interface NoticeService {
    void addNotice(Notice notice);

    List<Notice> getAllNotice();

    Notice getNoticeById(Integer id);

    void updateNotice(Notice notice);

    void deleteNotice(Integer id);
}
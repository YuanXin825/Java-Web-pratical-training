package com.service;

import com.bean.Notice;
import com.bean.NoticeCategory;

import java.util.List;

public interface NoticeService {

    void addNotice(Notice notice);

    List<Notice> getAllNotice();

    List<Notice> getAllNoticeForOwner(Integer userId);

    Notice getNoticeById(Integer id);

    void updateNotice(Notice notice);

    void deleteNotice(Integer id);

    void updateTop(Integer id, Integer isTop);

    int countUnread(Integer userId);

    void markRead(Integer userId, Integer noticeId);

    List<NoticeCategory> getAllCategories();

    NoticeCategory getCategoryById(Integer id);

    void addCategory(NoticeCategory category);

    void updateCategory(NoticeCategory category);

    void deleteCategory(Integer id);
}

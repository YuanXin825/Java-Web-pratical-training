package com.impl;

import com.dao.NoticeDao;
import com.entity.Notice;
import com.service.NoticeService;

import java.util.List;

public class NoticeServiceImpl implements NoticeService {

    private NoticeDao noticeDao = new NoticeDao();

    @Override
    public void addNotice(Notice notice) {
        noticeDao.addNotice(notice);
    }

    @Override
    public List<Notice> getAllNotice() {
        return noticeDao.findAllNotice();
    }

    @Override
    public Notice getNoticeById(Integer id) {
        return noticeDao.findNoticeById(id);
    }

    @Override
    public void updateNotice(Notice notice) {
        noticeDao.updateNotice(notice);
    }

    @Override
    public void deleteNotice(Integer id) {
        noticeDao.deleteNotice(id);
    }
}
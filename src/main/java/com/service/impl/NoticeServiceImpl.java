package com.service.impl;

import com.bean.Notice;
import com.bean.NoticeCategory;
import com.dao.NoticeCategoryDao;
import com.dao.NoticeDao;
import com.dao.NoticeReadDao;
import com.service.NoticeService;

import java.util.List;

public class NoticeServiceImpl implements NoticeService {

    private NoticeDao noticeDao = new NoticeDao();
    private NoticeCategoryDao categoryDao = new NoticeCategoryDao();
    private NoticeReadDao readDao = new NoticeReadDao();

    @Override
    public void addNotice(Notice notice) {
        noticeDao.addNotice(notice);
    }

    @Override
    public List<Notice> getAllNotice() {
        return noticeDao.findAllNotice();
    }

    @Override
    public List<Notice> getAllNoticeForOwner(Integer userId) {
        return noticeDao.findAllNoticeWithReadStatus(userId);
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
        readDao.deleteByNoticeId(id);
        noticeDao.deleteNotice(id);
    }

    @Override
    public void updateTop(Integer id, Integer isTop) {
        noticeDao.updateTop(id, isTop);
    }

    @Override
    public int countUnread(Integer userId) {
        return readDao.countUnread(userId);
    }

    @Override
    public void markRead(Integer userId, Integer noticeId) {
        readDao.markRead(userId, noticeId);
    }

    @Override
    public List<NoticeCategory> getAllCategories() {
        return categoryDao.findAll();
    }

    @Override
    public NoticeCategory getCategoryById(Integer id) {
        return categoryDao.findById(id);
    }

    @Override
    public void addCategory(NoticeCategory category) {
        categoryDao.add(category);
    }

    @Override
    public void updateCategory(NoticeCategory category) {
        categoryDao.update(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        if (noticeDao.countByCategoryId(id) > 0) {
            throw new IllegalStateException("该分类下还有公告，无法删除");
        }
        categoryDao.delete(id);
    }
}

package com.zx.server.server;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zx.server.dao.AuditDao;
import com.zx.server.domain.audit.Audit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditService {
  private final AuditDao auditDao;

  public AuditService(AuditDao auditDao) {
    this.auditDao = auditDao;
  }

  /**
   * 根据id获取单本书信息
   *
   * @param id 返回单行审计记录
   * @return Audit
   */
  public Audit getAuditById(Long id) {
    return auditDao.selectById(id);
  }
  /**
   * 仅为测试接口，请勿调用！！！
   *
   * @return 所有的书本信息
   */
  public List<Audit> getAllAudit() {
    return auditDao.selectList(null);
  }

  public IPage<Audit> getAuditByPage(Integer page, Integer size) {
    IPage<Audit> myPage = new Page<Audit>(page, size);
    return auditDao.selectPage(myPage, null);
  }


  @Transactional
  public boolean updateAudits(List<Audit> Audits) {
    int i = 0;
    for (Audit Audit : Audits) {
      i += auditDao.updateById(Audit);
    }
    return i == Audits.size();
  }

  @Transactional
  public boolean updateAudit(Audit Audit) {
    return auditDao.updateById(Audit) == 1;
  }

  @Transactional
  public boolean addAudits(List<Audit> Audits) {
    int i = 0;
    for (Audit Audit : Audits) {
      i += auditDao.insert(Audit);
    }
    return i == Audits.size();
  }

  /**
   * 用于删除给定的ids索引数组
   *
   * @param ids 被删除ID数组
   * @return 删除是否成功
   */
  @Transactional
  public boolean delAudits(List<Long> ids) {
    return ids.size() == auditDao.deleteBatchIds(ids);
  }

  public List<Audit> getAuditsByLikeName(String AuditName) {
    QueryWrapper<Audit> qw = new QueryWrapper<Audit>();
    qw.like("Audit_name", AuditName);
    return auditDao.selectList(qw);
  }
}

package moum.project.service;

import java.util.List;
import moum.project.vo.Alert;
import moum.project.vo.Report;

public interface ReportService {
  boolean add(Report report) throws Exception;

  List<Report> list() throws Exception;

  Report get(int no) throws Exception;

  boolean update(Report report) throws Exception;

  boolean delete(int no) throws Exception;
}

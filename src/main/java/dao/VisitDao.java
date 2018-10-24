package dao;

import model.Client;
import model.Visit;

import java.util.List;

public interface VisitDao {
    void add(Visit visit);
    List<Visit> findAll();
}

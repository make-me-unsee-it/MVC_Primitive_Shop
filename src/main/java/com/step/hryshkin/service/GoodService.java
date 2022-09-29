package com.step.hryshkin.service;

import com.step.hryshkin.model.Good;

import java.util.List;
import java.util.Optional;

public interface GoodService {

    List<Good> getAll();

    Optional<Good> getById(long id);
}

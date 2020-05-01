package com.dez.room.Repo;

import com.dez.room.data.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepo extends CrudRepository<Country, Short> {
    Iterable<Country> findAllByOrderByNameEnAsc();
}

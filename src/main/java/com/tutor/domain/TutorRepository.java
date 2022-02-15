package com.tutor.domain;

public interface TutorRepository {

    void save(Tutor tutor);

    void update(Tutor tutor);

    void delete(Tutor tutor);

}

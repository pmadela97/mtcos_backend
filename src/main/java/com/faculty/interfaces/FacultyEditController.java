package com.faculty.interfaces;

import com.faculty.application.FacultyApplicationService;
import com.faculty.application.FacultyQueries;
import com.faculty.domain.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;

@RestController
@RequestMapping("/api/faculty/form")
public class FacultyEditController {

    private final FacultyQueries facultyQueries;
    private final FacultyApplicationService facultyApplicationService;;

    @Autowired
    public FacultyEditController(FacultyQueries facultyQueries, FacultyApplicationService facultyApplicationService) {

        Assert.notNull(facultyApplicationService, "userService must be not null");
        Assert.notNull(facultyQueries, "facultyQueries must be not null");

        this.facultyApplicationService = facultyApplicationService;
        this.facultyQueries = facultyQueries;
    }

    @GetMapping
    private FacultyForm initView(@RequestParam(name = "id", required = false) @Nullable Long id) {

        if (id != null && id != 0) {

            Faculty faculty = facultyQueries.findEntityById(id)
                    .orElseThrow(EntityNotFoundException::new);

            return new FacultyForm(faculty.getId(), faculty.getName());
        }
        else {

            return new FacultyForm();
        }
    }

    @PostMapping
    private ResponseEntity<?> changeFacultyName(@RequestBody FacultyForm facultyForm, Principal principal) {

       try {
           if (facultyForm.getId() == 0) {

               facultyApplicationService.addNewFaculty(facultyForm.getName(), principal.getName());
           }
           else {

               facultyApplicationService.editFaculty(facultyForm.getId(), facultyForm.getName(), principal.getName());
           }

            return ResponseEntity.ok().build();
        }
       catch (EntityNotFoundException e) {

           return ResponseEntity.badRequest().build();
       }
    }
}
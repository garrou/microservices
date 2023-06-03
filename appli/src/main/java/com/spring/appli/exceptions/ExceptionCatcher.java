package com.spring.appli.exceptions;

import com.spring.appli.dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(HttpServletRequest request, MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BadgeNotFoundException.class)
    public ResponseEntity<ResponseDto> handleBadgeNotFoundException(HttpServletRequest request, BadgeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(CompetitionNotFoundException.class)
    public ResponseEntity<ResponseDto> handleCompetitionNotFoundException(HttpServletRequest request, CompetitionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ResponseDto> handleCourseNotFoundException(HttpServletRequest request, CourseNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(ParticipationNotFoundException.class)
    public ResponseEntity<ResponseDto> handleParticipationNotFoundException(HttpServletRequest request, ParticipationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ResponseDto> handlePersonNotFoundException(HttpServletRequest request, PersonNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(BadTokenException.class)
    public ResponseEntity<ResponseDto> handleBadTokenException(HttpServletRequest request, BadTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(WrongAuthentificationException.class)
    public ResponseEntity<ResponseDto> handleWrongAuthentificationException(HttpServletRequest request, WrongAuthentificationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDto> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(StudentAlreadyOnCompetitionException.class)
    public ResponseEntity<ResponseDto> handleStudentAlreadyOnCompetitionException(HttpServletRequest request, StudentAlreadyOnCompetitionException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(StudentAlreadyOnCourseException.class)
    public ResponseEntity<ResponseDto> handleStudentAlreadyOnCourseException(HttpServletRequest request, StudentAlreadyOnCourseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(PseudoAlreadyExistException.class)
    public ResponseEntity<ResponseDto> handlePseudoAlreadyExistException(HttpServletRequest request, PseudoAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(PresenceNotFoundException.class)
    public ResponseEntity<ResponseDto> handlePresenceNotFoundException(HttpServletRequest request, PresenceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(TooEarlyException.class)
    public ResponseEntity<ResponseDto> handleTooEarlyException(HttpServletRequest request, TooEarlyException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(e.getMessage()));
    }


}
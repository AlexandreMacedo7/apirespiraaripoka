package com.macedo.apirespiraaripoka.util.exceptions;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe responsável por lidar com exceções globais na aplicação.
 */
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Manipula exceções do tipo {@link EntityNotFoundException}, que ocorrem quando
     * uma entidade não é encontrada.
     * 
     * @param ex      A exceção capturada.
     * @param request A requisição HTTP que causou a exceção.
     * @return Uma resposta HTTP com status 404 (Not Found) e uma mensagem de erro
     *         indicando que a entidade não foi encontrada.
     */

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> handleEntityNotFoundException(EntityNotFoundException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError error = createStandardError(status, "Entidade não encontrada", ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    /**
     * Manipula exceções do tipo {@link MethodArgumentNotValidException}, que
     * ocorrem quando a validação dos argumentos
     * de um método de controle falha.
     * 
     * @param ex A exceção capturada.
     * @return Uma resposta HTTP com status 400 (Bad Request) e uma lista de erros
     *         de validação.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        List<DataValidationErro> errors = ex.getFieldErrors().stream()
                .map(DataValidationErro::new)
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(errors);

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Manipula exceções do tipo {@link Exception} que não foram capturadas por
     * outros manipuladores de exceção.
     * 
     * @param ex      A exceção capturada.
     * @param request A requisição HTTP que causou a exceção.
     * @return Uma resposta HTTP com status 500 (Internal Server Error) e uma
     *         mensagem de erro detalhada.
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleException(Exception ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError error = createStandardError(status, "Erro interno do servidor", ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    /**
     * Cria um objeto {@link StandardError} para representar um erro na aplicação.
     * 
     * @param status  O status HTTP que representa o erro.
     * @param error   A descrição do erro.
     * @param message A mensagem de erro detalhada.
     * @param path    O caminho da requisição que causou o erro.
     * @return Um objeto {@link StandardError} que encapsula as informações do erro.
     */

    private StandardError createStandardError(HttpStatus status, String error, String message, String path) {
        return new StandardError(Instant.now(), status.value(), error, message, path);
    }

    /**
     * Classe interna que representa uma resposta de erro de validação.
     */
    private record ValidationErrorResponse(List<DataValidationErro> errors) {
    }

    /**
     * Classe interna que representa um erro de validação de campo.
     */
    private record DataValidationErro(String field, String message) {

        /**
         * Construtor que cria um objeto {@code DataValidationErro} a partir de um erro
         * de campo.
         * 
         * @param error O erro de campo.
         */

        public DataValidationErro(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}

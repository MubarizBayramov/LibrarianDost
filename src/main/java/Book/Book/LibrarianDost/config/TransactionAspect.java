package Book.Book.LibrarianDost.config;


import Book.Book.LibrarianDost.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
public class TransactionAspect {


    @Before("execution(* Book.Book.LibrarianDost.service.TransactionService.*(..)) && args(buyerId,..)")
    public void validateBuyerId(Long buyerId) {
        if (buyerId == null || buyerId <= 0) {
            throw new BookException("Invalid buyer ID: " + buyerId);
        }
        log.info("Buyer ID validated: {}", buyerId);
    }


    @Before("execution(* Book.Book.LibrarianDost.service.TransactionService.buyBooks(..)) && args(buyerId, bookIds)")
    public void logBeforeBuying(Long buyerId, List<Long> bookIds) {
        log.info("Buyer {} is attempting to buy books: {}", buyerId, bookIds);
    }


    @AfterReturning(pointcut = "execution(* Book.Book.LibrarianDost.service.TransactionService.buyBooks(..))", returning = "response")
    public void logAfterBuying(Object response) {
        log.info("Purchase completed. Response: {}", response);
    }


    @Before("execution(* Book.Book.LibrarianDost.service.TransactionService.returnBooks(..)) && args(buyerId, transactionCodes)")
    public void logBeforeReturning(Long buyerId, List<String> transactionCodes) {
        log.info("Buyer {} is attempting to return books: {}", buyerId, transactionCodes);
    }


    @AfterReturning(pointcut = "execution(* Book.Book.LibrarianDost.service.TransactionService.returnBooks(..))", returning = "response")
    public void logAfterReturning(Object response) {
        log.info("Return completed. Response: {}", response);
    }


    @Around("execution(* Book.Book.LibrarianDost.service.TransactionService.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;
        log.info("{} executed in {} ms", joinPoint.getSignature(), duration);
        return proceed;
    }
}

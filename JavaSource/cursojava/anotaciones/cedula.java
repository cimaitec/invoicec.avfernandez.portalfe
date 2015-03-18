package cursojava.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CedulaValidator.class)
public @interface cedula
{
	//int atributo1();
	String message() default "Cedula Invalida";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
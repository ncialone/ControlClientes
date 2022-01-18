package com.test.prueba.web;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//implementamos interface para utilizar interceptores y manejar varios idiomas
@Configuration
public class WebConfig implements WebMvcConfigurer {

//etiquetamos bean y declaramos metodos, con esto generamos instancia y lo agrega
//al contenedor de Spring automaticamente. Utilizamos estas APIs para configurar
//correctamente el manejo de multiples idiomas, en la línea 18 podemos setear
//por defecto cual es el idioma que queremos que maneje nuestra web    
    
    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }
    
    @Bean LocaleChangeInterceptor localeChangeInterceptor(){
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registro){
        registro.addInterceptor(localeChangeInterceptor());
    }
    
    //este metodo obliga a hacer un redireccionamiento de login para ver la pagina de inicio
    //establecemos paths a usar sin necesidad de pasar por un controlador
    //si no lo agregamos, al querer acceder a este path no podremos redireccionar a esta vista
    @Override
    public void addViewControllers(ViewControllerRegistry registro){
        registro.addViewController("/").setViewName("index");
        registro.addViewController("/login");
        registro.addViewController("/errores/403").setViewName("/errores/403");
    }
}//END CLASS

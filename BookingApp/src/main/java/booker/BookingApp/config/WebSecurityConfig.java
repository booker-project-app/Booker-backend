package booker.BookingApp.config;


import booker.BookingApp.enums.Role;
import booker.BookingApp.security.RestAuthenticationEntryPoint;
import booker.BookingApp.security.TokenAuthenticationFilter;
import booker.BookingApp.service.implementation.CustomUserDetailsService;
import booker.BookingApp.util.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.core.context.SecurityContextHolder.MODE_INHERITABLETHREADLOCAL;
import static org.springframework.security.core.context.SecurityContextHolder.setStrategyName;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;



@Log4j2
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true
)
public class WebSecurityConfig {

    public WebSecurityConfig() {
        // Inherit security context in async function calls
        setStrategyName(MODE_INHERITABLETHREADLOCAL);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(jwtUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Autowired
    private TokenUtils tokenUtils;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http)
            throws Exception {

        http
                // Enable CORS
                .cors(withDefaults())
                //Disable CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Set session management to stateless
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(STATELESS))
                // Set unauthorized requests exception handler
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(restAuthenticationEntryPoint))
                //.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .authorizeHttpRequests(auth ->{
                            auth.requestMatchers(antMatcher("/api/users/login")).permitAll();
                            auth.requestMatchers(antMatcher("/api/users/signup")).permitAll();
                            auth.requestMatchers(antMatcher("/api/users/activate_profile/**")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/create_accommodation")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/accommodations/update_availability/**")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/images/upload-multiple")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/accommodations/search/**")).permitAll();  //searching for everyone
                            auth.requestMatchers(antMatcher("/api/accommodations")).hasAnyAuthority("OWNER", "ADMIN");          //viewing acc for everyone
                            auth.requestMatchers(antMatcher("/api/accommodations/add")).hasAuthority("OWNER");          //viewing acc for everyone
                            auth.requestMatchers(antMatcher("/api/accommodations/*")).permitAll();          //viewing acc for everyone
                            auth.requestMatchers(antMatcher("/api/accommodations/owner/*")).hasAuthority("OWNER");    //for owner
                            auth.requestMatchers(antMatcher("/api/accommodations/guest/**")).hasAuthority("GUEST");    //for owner
                            auth.requestMatchers(antMatcher("/api/accommodations/owner/{ownerId}/active")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/accommodations/admin/unapproved")).hasAuthority("ADMIN");    //for admin
                            auth.requestMatchers(antMatcher("/api/accommodations/approve/{accommodationId}")).hasAuthority("ADMIN");    //for admin
                            auth.requestMatchers(antMatcher("/api/accommodations/admin/remove/*")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/requests/owner/**")).hasAuthority("OWNER");      //for owner
                            auth.requestMatchers(antMatcher("/api/requests/guest/**")).hasAuthority("GUEST");      //for guest
                            auth.requestMatchers(antMatcher("/api/reservations/*")).hasAnyAuthority("GUEST", "OWNER");
                            auth.requestMatchers(antMatcher("/api/reservations/owner/**")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/reservations/accommodation/**")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/reservations/guest/**")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/report/owner/*/**")).permitAll();      //for owner
                            auth.requestMatchers(antMatcher("/api/notifications/*")).hasAnyAuthority("GUEST", "OWNER");      //they have notif
                            auth.requestMatchers(antMatcher("/api/accommodations/priceType/*")).permitAll();    //used in search - everyone
                            auth.requestMatchers(antMatcher("/api/prices/{accId}/{fromDate}/{toDate}/{people}")).permitAll();        //for guest - making reservation reqest
                            auth.requestMatchers(antMatcher("/api/guests/{guestId}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/guests/delete/{guestId}")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/owners/*")).permitAll();      //used in acc view - for everyone
                            auth.requestMatchers(antMatcher("/api/owners")).hasAuthority("OWNER");      //used in acc view - for everyone
                            auth.requestMatchers(antMatcher("/api/owners/delete/{ownerId}")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/admin/**")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/guests")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/amenities/**")).permitAll();      //used in search - for everyone
                            auth.requestMatchers(antMatcher("/api/availability/**")).permitAll();   //used in search - for everyone
                            auth.requestMatchers(antMatcher("/api/availability/*/**")).permitAll();   //used in search - for everyone
                            auth.requestMatchers(antMatcher("/api/accommodation_ratings/all/*/ratings")).permitAll();   //used in search - for everyone
                            auth.requestMatchers(antMatcher("/api/accommodation_comments/add_comment")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/accommodation_comments/all")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodation_comments/*/comments")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodation_comments/*/comment")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodation_comments/update")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/accommodation_comment/remove/*")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/accommodation_comments/all/reported")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/accommodation_comments/report/*")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/accommodation_ratings/all")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodation_ratings/*/rating")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodation_ratings/add_rating")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/accommodation_ratings/update")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/accommodation_ratings/remove/*")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/accommodation_ratings/all/reported")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/accommodation_ratings/report/*")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/owner_comments/all")).permitAll();
                            auth.requestMatchers(antMatcher("/api/owner_comments/*/comment")).permitAll();
                            auth.requestMatchers(antMatcher("/api/owner_comments/remove/*")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/owner_comments/add_comment")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/owner_comments/update")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/owner_comments/all/reported")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/owner_comments/all/*/comments")).permitAll();
                            auth.requestMatchers(antMatcher("/api/owner_comments/report/*")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/owner_ratings/all")).permitAll();
                            auth.requestMatchers(antMatcher("/api/owner_ratings/*/rating")).permitAll();
                            auth.requestMatchers(antMatcher("/api/owner_ratings/remove/*")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/owner_ratings/add_rating")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/owner_ratings/update")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/owner_ratings/*/ratings")).permitAll();
                            auth.requestMatchers(antMatcher("/api/owner_ratings/all/reported")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/owner_ratings/report/*")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/users/{userId}/upload_images")).permitAll();
                            auth.requestMatchers(antMatcher("/h2-console/**")).permitAll();     //for everyone


                            auth.anyRequest().authenticated();
                        }
                )
                .headers(headers ->
                        headers.frameOptions(FrameOptionsConfig::sameOrigin).disable())



                // Add JWT token filter
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils,  userDetailsService()), BasicAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers
                (
                        antMatcher(HttpMethod.GET, "/src/app/resources/images"),
                        antMatcher(HttpMethod.POST, "/api/users/signup"),
                        antMatcher(HttpMethod.POST, "/api/users/login"),
                        antMatcher(HttpMethod.PUT, "api/users/activate_profile/**"),
                        antMatcher(HttpMethod.POST, "/api/users/{userId}/upload_images"),
                        antMatcher(HttpMethod.PUT, "/api/accommodations"),
                        antMatcher(HttpMethod.PUT, "/api/accommodations/**"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/**"),
                        antMatcher(HttpMethod.POST, "/api/accommodations/search/**"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/search/**"),
                        antMatcher(HttpMethod.POST, "/api/accommodations/add"),
                        antMatcher(HttpMethod.PUT, "/api/requests/owner/**"),
                        antMatcher(HttpMethod.GET, "/api/requests/owner/**"),
                        antMatcher(HttpMethod.POST, "/api/requests"),
                        antMatcher(HttpMethod.GET, "/api/requests/**"),
                        antMatcher(HttpMethod.GET, "/api/notifications/**"),
                        antMatcher(HttpMethod.DELETE, "/api/requests/guest/**"),
                        antMatcher(HttpMethod.POST, "/api/reservations/**"),
                        antMatcher(HttpMethod.PUT, "/api/reservations/**"),
                        antMatcher(HttpMethod.GET, "/api/reservations/**"),
                        antMatcher(HttpMethod.DELETE, "/api/reservations/**"),
                        antMatcher(HttpMethod.DELETE, "/api/owners/**"),
                        antMatcher(HttpMethod.DELETE, "/api/guests/**"),
                        antMatcher(HttpMethod.GET, "/api/guests/**"),
                        antMatcher(HttpMethod.PUT, "/api/owners/**"),
                        antMatcher(HttpMethod.GET, "/api/owners/**"),
                        antMatcher(HttpMethod.PUT, "/api/guests"),
                        antMatcher(HttpMethod.PUT, "/api/guests/**"),
                        antMatcher(HttpMethod.PUT, "/api/owners"),
                        antMatcher(HttpMethod.POST, "/api/owners/**"),
                        antMatcher(HttpMethod.POST, "/api/guests/**"),
                        antMatcher(HttpMethod.POST, "/api/guests"),
                        antMatcher(HttpMethod.POST, "/api/availability/*"),
                        antMatcher(HttpMethod.GET, "/api/accommodation_comments/all"),
                        antMatcher(HttpMethod.GET, "/api/accommodation_comments/*/comments"),
                        antMatcher(HttpMethod.GET, "/api/accommodation_comments/*/comment"),
                        antMatcher(HttpMethod.GET, "/api/accommodation_ratings/all"),
                        antMatcher(HttpMethod.GET, "/api/accommodation_ratings/*/rating"),
                        antMatcher(HttpMethod.GET, "/api/owner_comments/all"),
                        antMatcher(HttpMethod.GET, "/api/owner_comments/*/comment"),
                        antMatcher(HttpMethod.GET, "/api/owner_comments/*/comments"),
                        antMatcher(HttpMethod.GET, "/api/owner_ratings/all"),
                        antMatcher(HttpMethod.GET, "/api/owner_ratings/*/rating"),
                        antMatcher(HttpMethod.GET, "/api/owner_ratings/*/ratings"),
                        antMatcher("/h2-console/**")
        );
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }





}

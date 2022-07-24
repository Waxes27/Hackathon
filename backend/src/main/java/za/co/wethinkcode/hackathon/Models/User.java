package za.co.wethinkcode.hackathon.Models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import za.co.wethinkcode.hackathon.Models.enums.AppUserRole;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private AppUserRole userRole;
    private Boolean locked = false;
    private Boolean enabled = false;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.userRole.name());
        return Collections.singleton(authority);
    }

    public User(String username, String password, String email, AppUserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",username);
        jsonObject.put("password",password);
        jsonObject.put("email",email);
        jsonObject.put("userRole",userRole);
        jsonObject.put("locked",locked);
        jsonObject.put("enabled",enabled);

        return jsonObject.toString();
    }
}
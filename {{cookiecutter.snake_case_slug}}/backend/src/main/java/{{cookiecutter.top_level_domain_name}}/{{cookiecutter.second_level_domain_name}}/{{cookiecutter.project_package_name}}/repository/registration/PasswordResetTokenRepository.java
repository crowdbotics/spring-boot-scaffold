package {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.repository.registration;

import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.registration.PasswordResetToken;
import {{cookiecutter.top_level_domain_name}}.{{cookiecutter.second_level_domain_name}}.{{cookiecutter.project_package_name}}.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.stream.Stream;

/**
 * <h1>Password Reset Token Repository</h1>
 * 
 * <p>Spring repository for type {@link PasswordResetToken}.</p>
 * 
 * @author crowdbotics.com
 */
public interface PasswordResetTokenRepository 
    extends JpaRepository<PasswordResetToken, {{cookiecutter.entity_id_type}}>
        , JpaSpecificationExecutor<PasswordResetToken>
{

    PasswordResetToken findByToken( String _token );

    PasswordResetToken findByUser( User _user );

    Stream<PasswordResetToken> findAllByExpiryDateLessThan( Date _now );

    void deleteByExpiryDateLessThan( Date _now );

    @Modifying
    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince( Date _now );
}

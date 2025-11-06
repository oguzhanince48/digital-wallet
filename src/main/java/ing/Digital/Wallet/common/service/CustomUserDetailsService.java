package ing.Digital.Wallet.common.service;

import ing.Digital.Wallet.common.exception.WalletApiBusinessException;
import ing.Digital.Wallet.common.model.CustomUserDetails;
import ing.Digital.Wallet.user.jpa.entity.UserEntity;
import ing.Digital.Wallet.user.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws WalletApiBusinessException {
        UserEntity userEntity = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new WalletApiBusinessException("wallet-api.user.notFound"));
        return new CustomUserDetails(userEntity);
    }
}

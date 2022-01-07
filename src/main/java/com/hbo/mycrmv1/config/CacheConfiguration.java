package com.hbo.mycrmv1.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.hbo.mycrmv1.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.hbo.mycrmv1.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.hbo.mycrmv1.domain.User.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.Authority.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.User.class.getName() + ".authorities");
            createCache(cm, com.hbo.mycrmv1.domain.Article.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.UniteArticle.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.UniteArticle.class.getName() + ".articles");
            createCache(cm, com.hbo.mycrmv1.domain.LigneCmdFournisseur.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.LigneCmdFournisseur.class.getName() + ".articles");
            createCache(cm, com.hbo.mycrmv1.domain.CommandeFournisseur.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.CommandeFournisseur.class.getName() + ".ligneCmdFournisseurs");
            createCache(cm, com.hbo.mycrmv1.domain.FactureAchat.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.LivraisonFr.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.LivraisonFr.class.getName() + ".ligneLivFournisseurs");
            createCache(cm, com.hbo.mycrmv1.domain.LivraisonFr.class.getName() + ".commandeFournisseurs");
            createCache(cm, com.hbo.mycrmv1.domain.LivraisonFr.class.getName() + ".factureAchats");
            createCache(cm, com.hbo.mycrmv1.domain.LigneLivFournisseur.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.LigneLivFournisseur.class.getName() + ".articles");
            createCache(cm, com.hbo.mycrmv1.domain.PayementFournisseur.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.PayementFournisseur.class.getName() + ".factureAchats");
            createCache(cm, com.hbo.mycrmv1.domain.ContactFournisseur.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.CiviliteFournisseur.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.CiviliteFournisseur.class.getName() + ".fournisseurs");
            createCache(cm, com.hbo.mycrmv1.domain.Fournisseur.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.Fournisseur.class.getName() + ".commandeFournisseurs");
            createCache(cm, com.hbo.mycrmv1.domain.Fournisseur.class.getName() + ".factureAchats");
            createCache(cm, com.hbo.mycrmv1.domain.Fournisseur.class.getName() + ".contactFournisseurs");
            createCache(cm, com.hbo.mycrmv1.domain.LigneCmdClient.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.LigneCmdClient.class.getName() + ".articles");
            createCache(cm, com.hbo.mycrmv1.domain.CommandeClient.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.CommandeClient.class.getName() + ".ligneCmdClients");
            createCache(cm, com.hbo.mycrmv1.domain.FactureVente.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.LivraisonCl.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.LivraisonCl.class.getName() + ".ligneLivClients");
            createCache(cm, com.hbo.mycrmv1.domain.LivraisonCl.class.getName() + ".commandeClients");
            createCache(cm, com.hbo.mycrmv1.domain.LivraisonCl.class.getName() + ".factureVentes");
            createCache(cm, com.hbo.mycrmv1.domain.LigneLivClient.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.LigneLivClient.class.getName() + ".articles");
            createCache(cm, com.hbo.mycrmv1.domain.PayementClient.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.PayementClient.class.getName() + ".factureAchats");
            createCache(cm, com.hbo.mycrmv1.domain.ContactClient.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.CiviliteClient.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.CiviliteClient.class.getName() + ".clients");
            createCache(cm, com.hbo.mycrmv1.domain.Client.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.Client.class.getName() + ".commandeClients");
            createCache(cm, com.hbo.mycrmv1.domain.Client.class.getName() + ".factureAchats");
            createCache(cm, com.hbo.mycrmv1.domain.Client.class.getName() + ".contactClients");
            createCache(cm, com.hbo.mycrmv1.domain.Carts.class.getName());
            createCache(cm, com.hbo.mycrmv1.domain.Category.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}

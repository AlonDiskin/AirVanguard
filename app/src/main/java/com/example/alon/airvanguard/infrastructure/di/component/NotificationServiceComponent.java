package com.example.alon.airvanguard.infrastructure.di.component;

import com.example.alon.airvanguard.infrastructure.app.DistressCallNotificationService;
import com.example.alon.airvanguard.infrastructure.di.module.NotificationServiceModule;
import com.example.alon.airvanguard.infrastructure.di.scope.ServiceScope;

import dagger.Component;

/**
 * Created by alon on 6/26/17.
 */
@ServiceScope
@Component(modules = NotificationServiceModule.class,dependencies = AppComponent.class)
public interface NotificationServiceComponent {

    void inject(DistressCallNotificationService service);
}

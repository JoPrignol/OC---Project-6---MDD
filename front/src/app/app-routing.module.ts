import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { PostsComponent } from './pages/posts/posts.component';
import { PostDetailsComponent } from './pages/postDetails/postDetails.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [

  // Route par défaut pour la page d'accueil
  { path: '', component: HomeComponent },

  // Route pour la page des topics
  // { path: '**', redirectTo: '/topics', pathMatch: 'full' },
  {
    path: 'topics',
    canActivate: [AuthGuard],
    component: TopicsComponent
  },

  {
    path: 'posts',
    canActivate: [AuthGuard],
    component: PostsComponent
  },

  {
    path: 'posts/:id',
    component: PostDetailsComponent
  },

  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule),
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

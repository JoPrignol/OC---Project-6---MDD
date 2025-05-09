// posts-list.component.ts
import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/posts/posts.service';
import { Post } from 'src/app/models/post.model';
import { AuthService } from 'src/app/auth/services/auth.service';
import { User } from 'src/app/interfaces/user.interface';
import { DEFAULT_POST } from 'src/app/models/post.model';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentService } from 'src/app/services/comments/comments.service';
import { Topic } from 'src/app/models/topic.model';
import { TopicsService } from 'src/app/services/topics/topics.service';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-posts',
  templateUrl: './createPost.component.html',
  styleUrls: ['./createPost.component.scss']
})
export class CreatePostComponent implements OnInit {

  topics: Topic[] = [];
  postForm!: FormGroup;
  currentUserId: number = 0;
  postId: number = 0;
  successMessage: string = '';

  private destroy$ = new Subject<void>();

  constructor(
    private postService: PostService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private topicsService: TopicsService,
    public router: Router,
  ) {}

  ngOnInit(): void {
    this.authService.me()
    .pipe(takeUntil(this.destroy$))
    .subscribe(
      (user) => {
        this.currentUserId = user.id;
      },
      (error) => {
        console.error('Erreur lors de la récupération de l’utilisateur', error);
      }
    );

    // Initialisation du formulaire
    this.postForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(255)]],
      content: ['', [Validators.required]],
      topicId: ['', [Validators.required]]
    });

    // Charger les thèmes disponibles
    this.topicsService.getAllTopics()
    .pipe(takeUntil(this.destroy$))
    .subscribe(
      (topics) => {
        this.topics = topics;
      },
      (error) => {
        console.error('Erreur lors de la récupération des thèmes', error);
      }
    );
  }

  createPost(): void {
    if (this.postForm.invalid) {
      return;
    }

    const postData: Post = {
      id: 0,
      title: this.postForm.value.title,
      content: this.postForm.value.content,
      userId: this.currentUserId,
      authorName: '',
      topicId: this.postForm.value.topicId,
      comments: [],
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
      topicName: ''
    };

    this.postService.createPost(postData)
    .pipe(takeUntil(this.destroy$))
    .subscribe(
      (newPost) => {
        // Réinitialiser le formulaire après la soumission
        this.postForm.reset();
        this.successMessage = 'Post créé avec succès !';
        setTimeout(() => {
          this.successMessage = '';
        }, 3000);
      },
      (error) => {
        console.error('Erreur lors de la création du post', error);
      }
    );
    this.router.navigate(['/posts']);
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

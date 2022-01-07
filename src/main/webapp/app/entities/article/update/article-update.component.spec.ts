import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ArticleService } from '../service/article.service';
import { IArticle, Article } from '../article.model';
import { ILigneCmdFournisseur } from 'app/entities/ligne-cmd-fournisseur/ligne-cmd-fournisseur.model';
import { LigneCmdFournisseurService } from 'app/entities/ligne-cmd-fournisseur/service/ligne-cmd-fournisseur.service';
import { ILigneLivFournisseur } from 'app/entities/ligne-liv-fournisseur/ligne-liv-fournisseur.model';
import { LigneLivFournisseurService } from 'app/entities/ligne-liv-fournisseur/service/ligne-liv-fournisseur.service';
import { IUniteArticle } from 'app/entities/unite-article/unite-article.model';
import { UniteArticleService } from 'app/entities/unite-article/service/unite-article.service';
import { ILigneCmdClient } from 'app/entities/ligne-cmd-client/ligne-cmd-client.model';
import { LigneCmdClientService } from 'app/entities/ligne-cmd-client/service/ligne-cmd-client.service';
import { ILigneLivClient } from 'app/entities/ligne-liv-client/ligne-liv-client.model';
import { LigneLivClientService } from 'app/entities/ligne-liv-client/service/ligne-liv-client.service';

import { ArticleUpdateComponent } from './article-update.component';

describe('Article Management Update Component', () => {
  let comp: ArticleUpdateComponent;
  let fixture: ComponentFixture<ArticleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let articleService: ArticleService;
  let ligneCmdFournisseurService: LigneCmdFournisseurService;
  let ligneLivFournisseurService: LigneLivFournisseurService;
  let uniteArticleService: UniteArticleService;
  let ligneCmdClientService: LigneCmdClientService;
  let ligneLivClientService: LigneLivClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ArticleUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ArticleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ArticleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    articleService = TestBed.inject(ArticleService);
    ligneCmdFournisseurService = TestBed.inject(LigneCmdFournisseurService);
    ligneLivFournisseurService = TestBed.inject(LigneLivFournisseurService);
    uniteArticleService = TestBed.inject(UniteArticleService);
    ligneCmdClientService = TestBed.inject(LigneCmdClientService);
    ligneLivClientService = TestBed.inject(LigneLivClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LigneCmdFournisseur query and add missing value', () => {
      const article: IArticle = { id: 456 };
      const ligneCmdFournisseur: ILigneCmdFournisseur = { id: 37235 };
      article.ligneCmdFournisseur = ligneCmdFournisseur;

      const ligneCmdFournisseurCollection: ILigneCmdFournisseur[] = [{ id: 26004 }];
      jest.spyOn(ligneCmdFournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: ligneCmdFournisseurCollection })));
      const additionalLigneCmdFournisseurs = [ligneCmdFournisseur];
      const expectedCollection: ILigneCmdFournisseur[] = [...additionalLigneCmdFournisseurs, ...ligneCmdFournisseurCollection];
      jest.spyOn(ligneCmdFournisseurService, 'addLigneCmdFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ article });
      comp.ngOnInit();

      expect(ligneCmdFournisseurService.query).toHaveBeenCalled();
      expect(ligneCmdFournisseurService.addLigneCmdFournisseurToCollectionIfMissing).toHaveBeenCalledWith(
        ligneCmdFournisseurCollection,
        ...additionalLigneCmdFournisseurs
      );
      expect(comp.ligneCmdFournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LigneLivFournisseur query and add missing value', () => {
      const article: IArticle = { id: 456 };
      const ligneLivFournisseur: ILigneLivFournisseur = { id: 1514 };
      article.ligneLivFournisseur = ligneLivFournisseur;

      const ligneLivFournisseurCollection: ILigneLivFournisseur[] = [{ id: 60696 }];
      jest.spyOn(ligneLivFournisseurService, 'query').mockReturnValue(of(new HttpResponse({ body: ligneLivFournisseurCollection })));
      const additionalLigneLivFournisseurs = [ligneLivFournisseur];
      const expectedCollection: ILigneLivFournisseur[] = [...additionalLigneLivFournisseurs, ...ligneLivFournisseurCollection];
      jest.spyOn(ligneLivFournisseurService, 'addLigneLivFournisseurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ article });
      comp.ngOnInit();

      expect(ligneLivFournisseurService.query).toHaveBeenCalled();
      expect(ligneLivFournisseurService.addLigneLivFournisseurToCollectionIfMissing).toHaveBeenCalledWith(
        ligneLivFournisseurCollection,
        ...additionalLigneLivFournisseurs
      );
      expect(comp.ligneLivFournisseursSharedCollection).toEqual(expectedCollection);
    });

    it('Should call UniteArticle query and add missing value', () => {
      const article: IArticle = { id: 456 };
      const uniteArticle: IUniteArticle = { id: 4884 };
      article.uniteArticle = uniteArticle;

      const uniteArticleCollection: IUniteArticle[] = [{ id: 4363 }];
      jest.spyOn(uniteArticleService, 'query').mockReturnValue(of(new HttpResponse({ body: uniteArticleCollection })));
      const additionalUniteArticles = [uniteArticle];
      const expectedCollection: IUniteArticle[] = [...additionalUniteArticles, ...uniteArticleCollection];
      jest.spyOn(uniteArticleService, 'addUniteArticleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ article });
      comp.ngOnInit();

      expect(uniteArticleService.query).toHaveBeenCalled();
      expect(uniteArticleService.addUniteArticleToCollectionIfMissing).toHaveBeenCalledWith(
        uniteArticleCollection,
        ...additionalUniteArticles
      );
      expect(comp.uniteArticlesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LigneCmdClient query and add missing value', () => {
      const article: IArticle = { id: 456 };
      const ligneCmdClient: ILigneCmdClient = { id: 78932 };
      article.ligneCmdClient = ligneCmdClient;

      const ligneCmdClientCollection: ILigneCmdClient[] = [{ id: 21927 }];
      jest.spyOn(ligneCmdClientService, 'query').mockReturnValue(of(new HttpResponse({ body: ligneCmdClientCollection })));
      const additionalLigneCmdClients = [ligneCmdClient];
      const expectedCollection: ILigneCmdClient[] = [...additionalLigneCmdClients, ...ligneCmdClientCollection];
      jest.spyOn(ligneCmdClientService, 'addLigneCmdClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ article });
      comp.ngOnInit();

      expect(ligneCmdClientService.query).toHaveBeenCalled();
      expect(ligneCmdClientService.addLigneCmdClientToCollectionIfMissing).toHaveBeenCalledWith(
        ligneCmdClientCollection,
        ...additionalLigneCmdClients
      );
      expect(comp.ligneCmdClientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LigneLivClient query and add missing value', () => {
      const article: IArticle = { id: 456 };
      const ligneLivClient: ILigneLivClient = { id: 41887 };
      article.ligneLivClient = ligneLivClient;

      const ligneLivClientCollection: ILigneLivClient[] = [{ id: 47900 }];
      jest.spyOn(ligneLivClientService, 'query').mockReturnValue(of(new HttpResponse({ body: ligneLivClientCollection })));
      const additionalLigneLivClients = [ligneLivClient];
      const expectedCollection: ILigneLivClient[] = [...additionalLigneLivClients, ...ligneLivClientCollection];
      jest.spyOn(ligneLivClientService, 'addLigneLivClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ article });
      comp.ngOnInit();

      expect(ligneLivClientService.query).toHaveBeenCalled();
      expect(ligneLivClientService.addLigneLivClientToCollectionIfMissing).toHaveBeenCalledWith(
        ligneLivClientCollection,
        ...additionalLigneLivClients
      );
      expect(comp.ligneLivClientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const article: IArticle = { id: 456 };
      const ligneCmdFournisseur: ILigneCmdFournisseur = { id: 95198 };
      article.ligneCmdFournisseur = ligneCmdFournisseur;
      const ligneLivFournisseur: ILigneLivFournisseur = { id: 76037 };
      article.ligneLivFournisseur = ligneLivFournisseur;
      const uniteArticle: IUniteArticle = { id: 56583 };
      article.uniteArticle = uniteArticle;
      const ligneCmdClient: ILigneCmdClient = { id: 81493 };
      article.ligneCmdClient = ligneCmdClient;
      const ligneLivClient: ILigneLivClient = { id: 64786 };
      article.ligneLivClient = ligneLivClient;

      activatedRoute.data = of({ article });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(article));
      expect(comp.ligneCmdFournisseursSharedCollection).toContain(ligneCmdFournisseur);
      expect(comp.ligneLivFournisseursSharedCollection).toContain(ligneLivFournisseur);
      expect(comp.uniteArticlesSharedCollection).toContain(uniteArticle);
      expect(comp.ligneCmdClientsSharedCollection).toContain(ligneCmdClient);
      expect(comp.ligneLivClientsSharedCollection).toContain(ligneLivClient);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Article>>();
      const article = { id: 123 };
      jest.spyOn(articleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ article });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: article }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(articleService.update).toHaveBeenCalledWith(article);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Article>>();
      const article = new Article();
      jest.spyOn(articleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ article });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: article }));
      saveSubject.complete();

      // THEN
      expect(articleService.create).toHaveBeenCalledWith(article);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Article>>();
      const article = { id: 123 };
      jest.spyOn(articleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ article });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(articleService.update).toHaveBeenCalledWith(article);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackLigneCmdFournisseurById', () => {
      it('Should return tracked LigneCmdFournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLigneCmdFournisseurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLigneLivFournisseurById', () => {
      it('Should return tracked LigneLivFournisseur primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLigneLivFournisseurById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackUniteArticleById', () => {
      it('Should return tracked UniteArticle primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackUniteArticleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLigneCmdClientById', () => {
      it('Should return tracked LigneCmdClient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLigneCmdClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLigneLivClientById', () => {
      it('Should return tracked LigneLivClient primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLigneLivClientById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});

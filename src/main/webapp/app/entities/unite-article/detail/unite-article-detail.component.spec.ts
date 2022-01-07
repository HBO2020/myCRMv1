import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UniteArticleDetailComponent } from './unite-article-detail.component';

describe('UniteArticle Management Detail Component', () => {
  let comp: UniteArticleDetailComponent;
  let fixture: ComponentFixture<UniteArticleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UniteArticleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ uniteArticle: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UniteArticleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UniteArticleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load uniteArticle on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.uniteArticle).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

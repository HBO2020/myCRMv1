import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { UniteArticleService } from '../service/unite-article.service';

import { UniteArticleComponent } from './unite-article.component';

describe('UniteArticle Management Component', () => {
  let comp: UniteArticleComponent;
  let fixture: ComponentFixture<UniteArticleComponent>;
  let service: UniteArticleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [UniteArticleComponent],
    })
      .overrideTemplate(UniteArticleComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UniteArticleComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UniteArticleService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.uniteArticles?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LivraisonClDetailComponent } from './livraison-cl-detail.component';

describe('LivraisonCl Management Detail Component', () => {
  let comp: LivraisonClDetailComponent;
  let fixture: ComponentFixture<LivraisonClDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LivraisonClDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ livraisonCl: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LivraisonClDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LivraisonClDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load livraisonCl on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.livraisonCl).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LivraisonFrDetailComponent } from './livraison-fr-detail.component';

describe('LivraisonFr Management Detail Component', () => {
  let comp: LivraisonFrDetailComponent;
  let fixture: ComponentFixture<LivraisonFrDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LivraisonFrDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ livraisonFr: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LivraisonFrDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LivraisonFrDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load livraisonFr on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.livraisonFr).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
